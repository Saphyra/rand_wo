(function ColumnController(){
    events.LABELS_CHANGED = "labels_changed";

    let loadedKeys = null;
    const storedSettings = new function(){
        const storage = {};

        this.getStorage = function(){return storage};

        this.getStoredSetting = function(keyId){
            if(!storage[keyId]){
                storage[keyId] = new StoredSetting(true, false);
            }

            return storage[keyId];
        }
    }

    window.columnController = new function(){
        this.getStoredSettings = function(){return storedSettings};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LABELS_CHANGED},
        function(){
            const request = new Request(HttpMethod.POST, Mapping.GET_KEYS_FOR_LABELS, labelController.getSelectedLabels());
                request.convertResponse = function(response){
                    const keys = JSON.parse(response.body);
                    keys.sort(function(a, b){return a.keyValue.localeCompare(b.keyValue)});
                    return keys;
                }
                request.processValidResponse = function(keys){
                    loadedKeys = mapKeys(keys);
                    displayKeys();
                }
            dao.sendRequestAsync(request);

            function mapKeys(keys){
                const result = {};

                for(let kIndex in keys){
                    const key = keys[kIndex];
                    result[key.keyId] = key.keyValue;
                }

                return result;
            }
        }
    ));

    function displayKeys(){
        const container = document.getElementById("columns");
            container.innerHTML = "";

        Object.keys(loadedKeys).length == 0 ? $("#no-columns").show() : $("#no-columns").hide();
        
        for(let keyId in loadedKeys){
            const storedSetting = storedSettings.getStoredSetting(keyId);

            const keyItem = document.createElement("DIV");
                keyItem.classList.add("column");

                const keyValue = document.createElement("DIV");
                    keyValue.innerHTML = loadedKeys[keyId];
                    keyValue.classList.add("column-name");
            keyItem.appendChild(keyValue);

                const labels = document.createElement("DIV");
                    labels.classList.add("column-labels");

                    const displayedLabel = document.createElement("LABEL");
                        const displayedBox = document.createElement("INPUT");
                            displayedBox.type = "checkbox";
                            displayedBox.classList.add("displayed-checkbox");
                            displayedBox.checked = storedSetting.isDisplayed();
                            displayedBox.value = keyId;
                            displayedBox.onchange = function(){
                                storedSettings.getStoredSetting(keyId).setDisplayed(displayedBox.checked);
                            }
                    displayedLabel.appendChild(displayedBox);
                        const displayedSpan = document.createElement("SPAN");
                            displayedSpan.innerHTML = Localization.getAdditionalContent("is-displayed");
                    displayedLabel.appendChild(displayedSpan);
                labels.appendChild(displayedLabel);

                    const hiddenLabel = document.createElement("LABEL");
                        const hiddenBox = document.createElement("INPUT");
                            hiddenBox.type = "checkbox";
                            hiddenBox.classList.add("hidden-checkbox");
                            hiddenBox.checked = storedSetting.isHidden();
                            hiddenBox.value = keyId;
                            hiddenBox.onchange = function(){
                                storedSettings.getStoredSetting(keyId).setHidden(hiddenBox.checked);
                            }
                    hiddenLabel.appendChild(hiddenBox);
                        const hiddenSpan = document.createElement("SPAN");
                            hiddenSpan.innerHTML = Localization.getAdditionalContent("is-hidden");
                    hiddenLabel.appendChild(hiddenSpan);
                labels.appendChild(hiddenLabel);

            keyItem.appendChild(labels);
            container.appendChild(keyItem);
        }
    }
    
    function StoredSetting(d, h){
        let displayed = d;
        let hidden = h;
        
        this.isDisplayed = function(){return displayed};
        this.isHidden = function(){return hidden};

        this.setDisplayed = function(d){displayed = d};
        this.setHidden = function(h){hidden = h};
    }
})();