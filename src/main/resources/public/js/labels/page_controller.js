(function PageController(){
    scriptLoader.loadScript("/js/labels/labels_controller.js");

    const PAGE_NAME = "labels";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));
})();