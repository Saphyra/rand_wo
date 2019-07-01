(function PageController(){
    scriptLoader.loadScript("/js/keys/keys_controller.js");

    const PAGE_NAME = "keys";

    window.pageController = new function(){
        this.getPageName = function(){
            return PAGE_NAME;
        }
    }

    eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, PAGE_NAME));
})();