(function CreateItemService(){
    events.CREATE_ITEM = "create_item";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.CREATE_ITEM},
        function(){
            const addedLabels = labelController.getAddedLabels();
            const newLabels = labelController.getNewLabels();

            const addedKeys = keyController.getAddedKeys();
            const newKeys = keyController.getNewKeys();

            if(addedLabels.length + newLabels.length == 0){
                notificationService.showError(MessageCode.getMessage("no-labels"));
                return;
            }

            if(addedKeys.length + Object.keys(newKeys).length == 0){
                notificationService.showError(MessageCode.getMessage("no-keys"));
                return;
            }

            const body = {
                existingKeyValueIds: mapAddedKeys(addedKeys),
                newKeyValues: mapNewKeys(newKeys),
                existingLabelIds: addedLabels,
                newLabels: newLabels
            };

            const request = createRequest(body);
            dao.sendRequestAsync(request);

            function createRequest(body){
                if(!pageData.itemId.length){
                     const request = new Request(HttpMethod.PUT, Mapping.CREATE_ITEM, body);
                        request.processValidResponse = function(){
                            sessionStorage.successMessage = MessageCode.getMessage("item-created");
                            window.location.href = "/";
                        }
                    return request;
                } else{
                    const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.UPDATE_ITEM, pageData.itemId), body);
                        request.processValidResponse = function(){
                            sessionStorage.successMessage = MessageCode.getMessage("item-updated");
                            window.location.href = "/";
                        }
                    return request;
                }
            }

            function mapAddedKeys(addedKeys){
                const result = {};
                for(kIndex in addedKeys){
                    const keyId = addedKeys[kIndex];
                    const value = document.getElementById(keyId).value;
                    result[keyId] = value;
                }
                return result;
            }

            function mapNewKeys(newKeys){
                const result = {};

                for(let keyId in newKeys){
                    logService.logToConsole("New key id: " + keyId);
                    const value = document.getElementById(keyId).value;
                    result[newKeys[keyId]] = value;
                }

                return result;
            }
        }
    ));
})();