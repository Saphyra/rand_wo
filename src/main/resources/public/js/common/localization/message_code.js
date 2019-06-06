(function MessageCode(){
    const messageCodes = {};
    
    window.MessageCode = new function(){
        this.getMessage = getMessage;
    }
    
    function getMessage(messageCode){
        return messageCodes[messageCode] || "No message found for messageCode " + messageCode;
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(){
            localizationLoader.loadLocalization("message_codes", addMessageCodes)
        }
    ));
    
    function addMessageCodes(codes){
        for(let eindex in codes){
            messageCodes[eindex] = codes[eindex];
        }
        
        eventProcessor.processEvent(new Event(events.MESSAGE_CODES_LOADED));
    }
})();