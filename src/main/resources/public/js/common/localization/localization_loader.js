(function LocalizationLoader(){
    const DEFAULT_LOCALE = "hu";

    window.localizationLoader = new function(){
        this.loadLocalization = loadLocalization
    }

    function loadLocalization(fileName, successCallback){
        const request = new Request(HttpMethod.GET, getPath(localeService.getLocale(), fileName));
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(localization){
                successCallback(localization);
            }
            request.processInvalidResponse = function(){
                createFallBackQuery(fileName, successCallback);
            }
        dao.sendRequestAsync(request);

        function createFallBackQuery(fileName, successCallback){
            const request = new Request(HttpMethod.GET, getPath(DEFAULT_LOCALE, fileName));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(localization){
                    successCallback(localization);
                }
            dao.sendRequestAsync(request);
        }

        function getPath(locale, fileName){
            return "/i18n/" + locale + "/" + fileName + ".json";
        }
    }
})();