(function LocalizationMapCache(){
    const cache = {};

    window.localizationMapCache = new function(){
        this.getLocalizationMap = function(fileName){
            if(cache[fileName] == null){
                cache[fileName] = new LocalizationMap(fileName);
            }

            return cache[fileName];
        }
    }

    function LocalizationMap(fileName){
        let map = null;

        loadLocalization(fileName, function(localization){map = localization});

        this.getLocalization = function(key){
            return map[key] || throwException("IllegalArgument", "localization not found with key " + key);
        }
    }
})();

