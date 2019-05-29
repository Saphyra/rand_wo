function throwException(name, message){
    name = name == undefined ? "" : name;
    message = message == undefined ? "" : message;
    throw {name: name, message: message};
}

function getLanguage(){
    return navigator.language.toLowerCase().split("-")[0];
}

function getActualTimeStamp(){
    return Math.floor(new Date().getTime() / 1000);
}

function switchTab(clazz, id){
    $("." + clazz).hide();
    $("#" + id).show();
}

function orderMapByProperty(map, orderFunction){
    const result = {};
        const entryList = [];
        for(let id in map){
            entryList.push(new Entry(id, map[id]));
        }
        entryList.sort(orderFunction);
        
        for(let eindex in entryList){
            result[entryList[eindex].getKey()] = entryList[eindex].getValue();
        }

    return result;
    
    function Entry(k, v){
        const key = k;
        const value = v;
        
        this.getKey = function(){
            return key;
        }
        
        this.getValue = function(){
            return value;
        }
    }
}

function setIntervalImmediate(callBack, interval){
    callBack();
    return setInterval(callBack, interval);
}

function createSpan(text){
    if(text == null || text == undefined){
        text = "";
    }
    const label = document.createElement("SPAN");
        label.innerHTML = text;
    return label;
}

function getCookie(key){
    const cookies = document.cookie.split('; ');
    for(let cIndex in cookies){
        const cookie = cookies[cIndex].split("=");
        if(cookie[0] == key){
            return cookie[1];
        }
    }

    return null;
}