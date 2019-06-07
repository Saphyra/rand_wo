(function PageController(){
    scriptLoader.loadScript("/js/create_item/label_controller.js");

    const PAGE_NAME = "create_item";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));
})();