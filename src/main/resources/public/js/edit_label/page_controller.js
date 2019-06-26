(function PageController(){
    const PAGE_NAME = "edit_label";

    events.SAVE_CHANGES = "save_changes";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SAVE_CHANGES},
        function(){
            const newValue = $("#label-value").val();

            if(!newValue.length){
                notificationService.showError(MessageCode.getMessage("empty-label-value"));
                return;
            }

            const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.UPDATE_LABEL, pageData.labelId), newValue);
                request.processValidResponse = function(){
                    sessionStorage.successMessage = MessageCode.getMessage("label-updated");
                    window.location.href = "/labels";
                }
            dao.sendRequestAsync(request);
        }
    ));

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));

        document.getElementById("label-value").onkeyup = function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.SAVE_CHANGES));
            }
        }
    }
})();