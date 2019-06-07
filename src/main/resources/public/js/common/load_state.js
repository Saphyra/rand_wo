(function LoadState(){
    events.LOCALIZATION_LOADED = "localization_loaded";
    events.MESSAGE_CODES_LOADED = "message_codes_loaded";
    events.LOAD_STATE_CHANGED = "load_state_changed";
    
    window.LoadState = new function(){
        this.localizationLoaded = false;
        this.messageCodesLoaded = false;
    }
    
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