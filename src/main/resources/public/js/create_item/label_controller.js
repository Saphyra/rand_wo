(function LabelController(){
    events.ADD_NEW_LABEL = "add_new_label";
    events.ADD_EXISTING_LABEL = "add_existing_label";

    $(document).ready(init)

    let loadedLabels;
    let labelsCanBeAdd = [];
    const addedLabels = [];
    const newLabels = [];

    window.labelController = new function(){
        this.getAddedLabels = function(){return addedLabels};
        this.getNewLabels = function(){return newLabels};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ADD_NEW_LABEL},
        function(){
            const labelValue = $("#new-label-value").val();
            if(labelValue.length && validate(labelValue)){
                newLabels.push(labelValue);

                addToLabels(
                    labelValue,
                    newLabels,
                    function(){
                        newLabels.splice(newLabels.indexOf(labelValue), 1);
                    }
                );

                $("#new-label-value").val("");
            }

            function validate(labelValue){
                let isValid = true;

                for(let labelId in loadedLabels){
                    if(labelValue == loadedLabels[labelId]){
                        isValid = false;
                    }
                }

                for(let lIndex in newLabels){
                    if(labelValue == newLabels[lIndex]){
                        isValid = false;
                    }
                }

                if(!isValid){
                    notificationService.showError(MessageCode.getMessage("label-value-already-exists"));
                }

                return isValid;
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ADD_EXISTING_LABEL},
        function(event){
            const labelId = event.getPayload();
            addedLabels.push(labelId);
            labelsCanBeAdd.splice(labelsCanBeAdd.indexOf(labelId), 1);
            displayLabels();

            addToLabels(
                loadedLabels[labelId],
                addedLabels,
                function(){
                    labelsCanBeAdd.push(labelId);
                    displayLabels();
                    addedLabels.splice(addedLabels.indexOf(labelId), 1);
                }
            );
        }
    ));

    function addToLabels(labelValue, labelArray, callBack){
        const container = document.getElementById("current-labels");
            const label = document.createElement("BUTTON");
                label.innerHTML = labelValue;
        container.appendChild(label);

        label.onclick = function(){
            container.removeChild(label);
            if(addedLabels.length + newLabels.length == 0){
                $("#no-current-labels").show();
            }

            if(callBack){
                callBack();
            }
        }

        $("#no-current-labels").hide();
    }

    function loadLabels(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LABELS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(labels){
                loadedLabels = mapLabels(labels);
                labelsCanBeAdd = Object.keys(loadedLabels);
                displayLabels();
            }
        dao.sendRequestAsync(request);

        function mapLabels(labels){
            const result = {};

            for(let lIndex in labels){
                const label = labels[lIndex];
                result[label.labelId] = label.labelValue;
            }

            return result;
        }
    }

    function displayLabels(){
        labelsCanBeAdd.length ? $("#no-existing-labels").hide() : $("#no-existing-labels").show();

        const container = document.getElementById("existing-labels-container");
            container.innerHTML = "";

        labelsCanBeAdd.sort(function(a, b){return loadedLabels[a].localeCompare(loadedLabels[b])});

        for(let lIndex in labelsCanBeAdd){
            const labelId = labelsCanBeAdd[lIndex];

            const label = document.createElement("BUTTON");
                label.innerHTML = loadedLabels[labelId];
                label.onclick = function(){
                    eventProcessor.processEvent(new Event(events.ADD_EXISTING_LABEL, labelId));
                }
            container.appendChild(label);
        }
    }

    function init(){
        loadLabels();
        document.getElementById("new-label-value").onkeyup = function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.ADD_NEW_LABEL));
            }
        }
    }
})();