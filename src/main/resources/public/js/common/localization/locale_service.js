(function LocaleService(){
    events.SELECT_LANGUAGE = "select_language";

    const KEY_LOCALE = "locale";

    window.localeService = new function(){
        this.getLocale = getLocale;
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.SELECT_LANGUAGE},
        function(event){setLanguage(event.getPayload())}
    ));

    function setLanguage(language){
        const request = new Request(HttpMethod.POST, Mapping.SAVE_TO_OBJECT_STORE, {key: KEY_LOCALE, value: language});
            request.processValidResponse = function(){
                eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, pageController.getPageName()));
            }
        dao.sendRequestAsync(request);
    }

    function getLocale(){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.GET_OBJECT_FROM_STORE, KEY_LOCALE));

        if(response.status == ResponseStatus.OK){
            return JSON.parse(response.body).value;
        }else if(response.status == ResponseStatus.NOT_FOUND){
            return navigator.language.toLowerCase().split("-")[0];
        }else{
            throwException("UnknownApiResponse", response.toString());
        }
    }
})();