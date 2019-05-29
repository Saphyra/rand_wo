(function LoadState(){
    events.CATEGORY_NAMES_LOADED = "category_names_loaded";
    events.DESCRIPTION_LOADED = "description_loaded";
    events.ITEMS_LOADED = "items_loaded";
    events.LOCALIZATION_LOADED = "localization_loaded";
    events.MESSAGE_CODES_LOADED = "message_codes_loaded";
    events.LOAD_STATE_CHANGED = "load_state_changed";
    
    window.LoadState = new function(){
        this.categoryNamesLoaded = false;
        this.descriptionLoaded = false;
        this.itemsLoaded = false;
        this.localizationLoaded = false;
        this.messageCodesLoaded = false;
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.CATEGORY_NAMES_LOADED},
        function(){
            window.LoadState.categoryNamesLoaded = true;
            logService.logToConsole("category names loaded.");
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.DESCRIPTION_LOADED},
        function(){
            window.LoadState.descriptionLoaded = true;
            logService.logToConsole("description loaded.");
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.ITEMS_LOADED},
        function(){
            window.LoadState.itemsLoaded = true;
            logService.logToConsole("items loaded.");
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOCALIZATION_LOADED},
        function(){
            window.LoadState.localizationLoaded = true;
            logService.logToConsole("localization loaded.");
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        }
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.MESSAGE_CODES_LOADED},
        function(){
            window.LoadState.messageCodesLoaded = true;
            logService.logToConsole("message codes loaded.");
            eventProcessor.processEvent(new Event(events.LOAD_STATE_CHANGED));
        }
    ));
})();