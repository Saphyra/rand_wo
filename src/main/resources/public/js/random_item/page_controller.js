(function PageController(){
    scriptLoader.loadScript("/js/random_item/label_controller.js");
    scriptLoader.loadScript("/js/random_item/column_controller.js");
    scriptLoader.loadScript("/js/random_item/item_controller.js");

    const PAGE_NAME = "random_item";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));
})();