(function KeysController(){
    events.DISPLAY_KEYS = "display_keys";
    events.DELETE_SELECTED = "delete_selected";

    let keys = null;

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DISPLAY_KEYS},
        displayKeys
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DELETE_SELECTED},
        function(){
            const selectedKeys = [];
            $(".select-checkbox:checked:not(:disabled)").each(function(){selectedKeys.push(this.value)});

            if(!selectedKeys.length){
                notificationService.showError(Localization.getAdditionalContent("no-selected-keys"));
                return;
            }

            deleteKeys(selectedKeys);
        }
    ));

    function loadKeys(){
        const request = new Request(HttpMethod.GET, Mapping.GET_KEYS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(k){
                keys = k;
                displayKeys();
            }
        dao.sendRequestAsync(request);
    }

    function displayKeys(){
        const container = document.getElementById("keys");
            container.innerHTML = "";

        const searchText = $("#key-value").val().toLowerCase();
        const keysForDisplay = keys.filter(function(key){return key.keyValue.toLowerCase().indexOf(searchText) > -1});
            keysForDisplay.sort(function(a, b){
                return a.keyValue.localeCompare(b.keyValue);
            });

        keysForDisplay.length == 0 ? $("#no-keys").show() : $("#no-keys").hide();

        for(let lIndex in keysForDisplay){
            const keyData = keysForDisplay[lIndex];

            const key = document.createElement("DIV");
                key.classList.add("key");

                const table = document.createElement("TABLE");
                    const row = document.createElement("TR");

                        const checkBoxCell = document.createElement("TD");
                            const checkbox = document.createElement("INPUT");
                                checkbox.classList.add("select-checkbox");
                                checkbox.value = keyData.keyId;
                                checkbox.type = "checkbox";
                                checkbox.disabled = !keyData.deletable;
                        checkBoxCell.appendChild(checkbox);
                    row.appendChild(checkBoxCell);

                        const valueCell = document.createElement("TD");
                            $(valueCell).hover(
                                function(){valueCell.style.color = "red"},
                                function(){valueCell.style.color = "black"}
                            );
                            valueCell.classList.add("cursor-pointer");
                            valueCell.innerHTML = keyData.keyValue + " (" + keyData.items + ")";
                            valueCell.onclick = function(){
                                window.open("/items?key=" + keyData.keyId);
                            }
                    row.appendChild(valueCell);

                        const operationsCell = document.createElement("TD");
                            const editButton = document.createElement("BUTTON");
                                editButton.classList.add("display-block");
                                editButton.innerHTML = Localization.getAdditionalContent("edit-button");
                                editButton.onclick = function(){
                                    window.location.href = "/keys/edit/" + keyData.keyId;
                                }
                        operationsCell.appendChild(editButton);

                            const deleteButton = document.createElement("BUTTON");
                                deleteButton.classList.add("display-block");
                                deleteButton.innerHTML = Localization.getAdditionalContent("delete-button");
                                deleteButton.disabled = !keyData.deletable;
                                deleteButton.onclick = function(){
                                    deleteKeys([keyData.keyId]);
                                }
                        operationsCell.appendChild(deleteButton);
                    row.appendChild(operationsCell);

                table.appendChild(row);
            key.appendChild(table);
            container.appendChild(key);
        }
    }

    function deleteKeys(keyIds){
        if(!confirm(Localization.getAdditionalContent("confirm-deletion"))){
            return;
        }

        const request = new Request(HttpMethod.DELETE, Mapping.DELETE_KEYS, keyIds);
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("keys-deleted"));
                loadKeys();
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        loadKeys();
        document.getElementById("key-value").onkeyup = function(e){
            if(e.which == 13){
                displayKeys();
            }
        }

        document.getElementById("select-all-checkbox").onchange = function(){
            $(".select-checkbox:not(:disabled)").prop("checked", document.getElementById("select-all-checkbox").checked);
         }
    }
})();