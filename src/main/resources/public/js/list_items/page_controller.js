(function PageController(){
    scriptLoader.loadScript("/js/list_items/list_item_controller.js");

    const PAGE_NAME = "list_items";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));
})();