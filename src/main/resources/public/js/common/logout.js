(function Logout(){
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOGOUT},
        function(){
            const request = new Request(HttpMethod.GET, Mapping.LOGOUT);
                request.processValidResponse = function(){
                    notificationService.showSuccess(MessageCode.getMessage("server-stopped"));
                }
            dao.sendRequestAsync(request);
        }
    ));
})();