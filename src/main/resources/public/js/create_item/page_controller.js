(function PageController(){
    scriptLoader.loadScript("/js/create_item/label_controller.js");
    scriptLoader.loadScript("/js/create_item/key_controller.js");
    scriptLoader.loadScript("/js/create_item/item_controller.js");
    scriptLoader.loadScript("/js/create_item/create_item_service.js");

    const PAGE_NAME = "create_item";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOCALIZATION_LOADED},
        function(){
            if(pageData.itemId.length){
                document.title = Localization.getAdditionalContent("edit-item");
                document.getElementById("main-title").innerHTML = Localization.getAdditionalContent("edit-item");
            }
        },
        true
    ));
})();