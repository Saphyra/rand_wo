(function LabelsController(){
    events.DISPLAY_LABELS = "display_labels";
    events.DELETE_SELECTED = "delete_selected";

    let labels = null;

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DISPLAY_LABELS},
        displayLabels
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DELETE_SELECTED},
        function(){
            const selectedLabels = [];
            $(".select-checkbox:checked:not(:disabled)").each(function(){selectedLabels.push(this.value)});

            if(!selectedLabels.length){
                notificationService.showError(Localization.getAdditionalContent("no-selected-labels"));
                return;
            }

            deleteLabels(selectedLabels);
        }
    ));

    function loadLabels(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LABELS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(l){
                labels = l;
                displayLabels();
            }
        dao.sendRequestAsync(request);
    }

    function displayLabels(){
        const container = document.getElementById("labels");
            container.innerHTML = "";

        const searchText = $("#label-value").val().toLowerCase();
        const labelsForDisplay = labels.filter(function(label){return label.labelValue.toLowerCase().indexOf(searchText) > -1});
            labelsForDisplay.sort(function(a, b){
                return a.labelValue.localeCompare(b.labelValue);
            });

        labelsForDisplay.length == 0 ? $("#no-labels").show() : $("#no-labels").hide();

        for(let lIndex in labelsForDisplay){
            const labelData = labelsForDisplay[lIndex];

            const label = document.createElement("DIV");
                label.classList.add("label");

                const table = document.createElement("TABLE");
                    const row = document.createElement("TR");

                        const checkBoxCell = document.createElement("TD");
                            const checkbox = document.createElement("INPUT");
                                checkbox.classList.add("select-checkbox");
                                checkbox.value = labelData.labelId;
                                checkbox.type = "checkbox";
                        checkBoxCell.appendChild(checkbox);
                    row.appendChild(checkBoxCell);

                        const valueCell = document.createElement("TD");
                            $(valueCell).hover(
                                function(){valueCell.style.color = "red"},
                                function(){valueCell.style.color = "black"}
                            );
                            valueCell.classList.add("cursor-pointer");
                            valueCell.innerHTML = labelData.labelValue + " (" + labelData.items + ")";
                            valueCell.onclick = function(){
                                window.open("/items?label=" + labelData.labelId);
                            }
                    row.appendChild(valueCell);

                        const operationsCell = document.createElement("TD");
                            const editButton = document.createElement("BUTTON");
                                editButton.classList.add("display-block");
                                editButton.innerHTML = Localization.getAdditionalContent("edit-button");
                                editButton.onclick = function(){
                                    window.location.href = "/label/edit/" + labelData.labelId;
                                }
                        operationsCell.appendChild(editButton);

                            const deleteButton = document.createElement("BUTTON");
                                deleteButton.classList.add("display-block");
                                deleteButton.innerHTML = Localization.getAdditionalContent("delete-button");
                                deleteButton.onclick = function(){
                                    deleteLabels([labelData.labelId]);
                                }
                        operationsCell.appendChild(deleteButton);
                    row.appendChild(operationsCell);

                table.appendChild(row);
            label.appendChild(table);
            container.appendChild(label);
        }
    }

    function deleteLabels(labelIds){
        if(!confirm(Localization.getAdditionalContent("confirm-deletion"))){
            return;
        }

        const request = new Request(HttpMethod.DELETE, Mapping.DELETE_LABELS, labelIds);
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("labels-deleted"));
                loadLabels();
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        loadLabels();
        document.getElementById("label-value").onkeyup = function(e){
            if(e.which == 13){
                displayLabels();
            }
        }

        document.getElementById("select-all-checkbox").onchange = function(){
            $(".select-checkbox:not(:disabled)").prop("checked", document.getElementById("select-all-checkbox").checked);
         }
    }
})();