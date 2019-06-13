(function LabelController(){
    events.SELECT_ALL_LABELS = "select_all_labels"

    $(document).ready(init);

    let loadedLabels = null;
    let selectableLabels = null;
    const selectedLabels = [];

    window.labelController = new function(){
        this.getSelectedLabels = function(){return selectedLabels};
    }

    function loadLabels(){
        const request = new Request(HttpMethod.GET, Mapping.GET_LABELS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }

            request.processValidResponse = function(labels){
                loadedLabels = mapLabels(labels);
                selectableLabels = Object.keys(loadedLabels);
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
        const container = document.getElementById("selectable-labels");
            container.innerHTML = "";

        const displayableLabels = fetchDisplayableLabels(selectableLabels);

        displayableLabels.length == 0 ? $("#no-labels").show() : $("#no-labels").hide();

        for(let lIndex in displayableLabels){
            const labelId = displayableLabels[lIndex];

            const labelButton = document.createElement("BUTTON");
                labelButton.innerHTML = loadedLabels[labelId];
                labelButton.onclick = function(){
                    selectLabel(labelId);
                }
            container.appendChild(labelButton);
        }

        function fetchDisplayableLabels(labels){
            const searchValue = $("#search-label-value").val().toLowerCase();

            const filtered = labels.filter(function(label){
                return searchValue.length == 0 || loadedLabels[label].toLowerCase().indexOf(searchValue) > -1;
            });

            filtered.sort(function(a, b){
                return loadedLabels[a].localeCompare(loadedLabels[b]);
            });

            return filtered;
        }
    }

    function displaySelectedLabels(){
        const container = document.getElementById("selected-labels");
            container.innerHTML = "";

        selectedLabels.length == 0 ? $("#no-selected-labels").show() : $("#no-selected-labels").hide();

        selectedLabels.sort(function(a, b){return loadedLabels[a].localeCompare(loadedLabels[b])});

        for(let lIndex in selectedLabels){
            const labelId = selectedLabels[lIndex];

            const labelButton = document.createElement("BUTTON");
                labelButton.innerHTML = loadedLabels[labelId];
                labelButton.onclick = function(){
                    deselectLabel(labelId);
                }
            container.appendChild(labelButton);
        }
    }

    function selectLabel(labelId){
        selectableLabels.splice(selectableLabels.indexOf(labelId), 1);
        selectedLabels.push(labelId);

        displayLabels();
        displaySelectedLabels();
        eventProcessor.processEvent(new Event(events.LABELS_CHANGED));
    }

    function deselectLabel(labelId){
        selectedLabels.splice(selectedLabels.indexOf(labelId), 1);
        selectableLabels.push(labelId);

        displayLabels();
        displaySelectedLabels();

        eventProcessor.processEvent(new Event(events.LABELS_CHANGED));
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SELECT_ALL_LABELS},
        function(){
            for(let lIndex in selectableLabels){
                selectedLabels.push(selectableLabels[lIndex]);
            }

            selectableLabels = [];

            displayLabels();
            displaySelectedLabels();

            eventProcessor.processEvent(new Event(events.LABELS_CHANGED));
        }
    ));

    function init(){
        loadLabels();

        document.getElementById("search-label-value").onkeyup = displayLabels;
    }
})();