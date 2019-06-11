(function KeyController(){
    events.ADD_NEW_KEY = "add_new_key";
    events.REMOVE_COLUMN = "remove_column";

    const KEY_TYPE_NEW = "new";
    const KEY_TYPE_EXISTING = "existing";

    $(document).ready(init)

    let loadedKeys;
    let keysCanBeAdd = [];
    const addedKeys = [];
    const newKeys = {};

    window.keyController = new function(){
        this.getAddedKeys = function(){return addedKeys};
        this.getNewKeys = function(){return newKeys};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.ADD_NEW_KEY},
        function(){
            const keyValue = $("#new-key-value").val();
            if(keyValue.length && validate(keyValue)){
                const keyId = generateUUID();
                newKeys[keyId] = keyValue;

                eventProcessor.processEvent(new Event(
                    events.ADD_COLUMN,
                    {
                        id: keyId,
                        value: keyValue,
                        type: KEY_TYPE_NEW
                    }
                ));

                $("#new-key-value").val("");
            }

            function validate(keyValue){
                let isValid = true;

                for(let keyId in loadedKeys){
                    if(keyValue == loadedKeys[keyId]){
                        isValid = false;
                    }
                }

                for(let kIndex in newKeys){
                    if(keyValue == newKeys[kIndex]){
                        isValid = false;
                    }
                }

                if(!isValid){
                    notificationService.showError(MessageCode.getMessage("key-value-already-exists"));
                }

                return isValid;
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.REMOVE_COLUMN},
        function(event){
            const keyData = event.getPayload();

            switch(keyData.type){
                case KEY_TYPE_NEW:
                    delete newKeys[keyData.keyId];
                break;
                case KEY_TYPE_EXISTING:
                    addedKeys.splice(addedKeys.indexOf[keyData.keyId], 1);
                    keysCanBeAdd.push(keyData.keyId);
                    displayKeys();
                break;
                default:
                    throwException("IllegalArgument", "Unknown keyType: " + keyData.type);
                break;
            }
        }
    ));

    function loadKeys(){
        const request = new Request(HttpMethod.GET, Mapping.GET_KEYS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(keys){
                loadedKeys = mapKeys(keys);
                keysCanBeAdd = Object.keys(loadedKeys);
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

    function displayKeys(){
        keysCanBeAdd.length ? $("#no-existing-keys").hide() : $("#no-existing-keys").show();

        keysCanBeAdd.sort(function(a, b){return loadedKeys[a].localeCompare(loadedKeys[b])});

        const container = document.getElementById("existing-keys-container");
            container.innerHTML = "";

        for(let kIndex in keysCanBeAdd){
            const keyId = keysCanBeAdd[kIndex];
            const key = document.createElement("BUTTON");
                key.innerHTML = loadedKeys[keyId];
                key.onclick = function(){
                    eventProcessor.processEvent(new Event(
                        events.ADD_COLUMN,
                        {
                            id: keyId,
                            value: loadedKeys[keyId],
                            type: KEY_TYPE_EXISTING
                        }
                    ));

                    addedKeys.push(keyId),
                    keysCanBeAdd.splice(keysCanBeAdd.indexOf(keyId), 1);
                    displayKeys();
                }
            container.appendChild(key);
        }
    }

    function init(){
        loadKeys();
        document.getElementById("new-key-value").onkeyup = function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.ADD_NEW_KEY));
            }
        }
    }
})();