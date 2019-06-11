(function Localization(){
    let additionalContent = {};

    window.Localization = new function(){
        this.getAdditionalContent = function(contentId){
            return additionalContent[contentId] || throwException("IllegalArgument", "No additionalContent found with id " + contentId);
        }
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(pageName){
            localizationLoader.loadLocalization("common", fillPageWithText);
            localizationLoader.loadLocalization(
                pageName.getPayload(),
                function(content){
                    document.title = content.title;
                    fillPageWithText(content.staticText);
                    additionalContent = content.additionalContent;
                }
            );

            eventProcessor.processEvent(new Event(events.LOCALIZATION_LOADED));
        }
    ));

    function setTitle(title){
        document.title = title;
    }

    function fillPageWithText(texts){
        for(let id in texts){
            const element = document.getElementById(id);
            if(element){
                const localizations = texts[id];
                for(let lindex in localizations){
                    element[localizations[lindex].key] = localizations[lindex].message;
                }
            }else logService.logToConsole("Element not found with id " + id);
        }
    }
})();