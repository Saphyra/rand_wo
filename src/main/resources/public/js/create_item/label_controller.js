(function LabelController(){
    events.ADD_NEW_LABEL = "add_new_label";

    $(document).ready(init)

    let loadedLabels;
    let labelsCanBeAdd = [];
    const addedLabels = [];
    const newLabels = [];

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ADD_NEW_LABEL},
        function(){
            const labelValue = $("#new-label-value").val();
            if(labelValue.length && validate(labelValue)){
                newLabels.push(labelValue);

                addToLabels(labelValue);

                $("#new-label-value").val("");
                $("#no-current-labels").hide();
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

            function addToLabels(labelValue){
                const container = document.getElementById("current-labels");
                    const label = document.createElement("BUTTON");
                        label.innerHTML = labelValue;
                container.appendChild(label);

                label.onclick = function(){
                    container.removeChild(label);
                    newLabels.splice(newLabels.indexOf(labelValue), 1);
                    if(addedLabels.length + newLabels.length == 0){
                        $("#no-current-labels").show();
                    }
                }
            }
        }
    ));

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
        labelsCanBeAdd.length ? $("#no-saved-labels").hide() : $("#no-saved-labels").show();

        const container = document.getElementById("existing-labels-container");

        for(let lIndex in labelsCanBeAdd){
            const labelId = labelsCanBeAdd[lIndex];

            const label = document.createElement("BUTTON");
                label.innerHTML = loadedLabels[labelId];
                //TODO add label
            container.appendChild(label);
        }
    }

    function init(){
        loadLabels();
    }
})();