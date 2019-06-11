(function ListItemController(){
    scriptLoader.loadScript("/js/common/cache.js");

    events.SEARCH_ITEMS = "search_items";

    const labelCache = new Cache(loadLabel);
    const keyCache = new Cache(loadKey);

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SEARCH_ITEMS},
        loadItems
    ));

    function loadItems(){
        const searchByLabel = $("#search-by-label").val();
        const searchByKey = $("#search-by-key").val();
        const searchByValue = $("#search-by-value").val();

        const body = {
            searchByLabel: searchByLabel,
            searchByKey: searchByKey,
            searchByValue: searchByValue
        };
        const request = new Request(HttpMethod.POST, Mapping.GET_ITEMS, body);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(items){
                displayItems(items);
            }
        dao.sendRequestAsync(request);
    }

    function displayItems(items){
        items.length ? $("#no-items").hide() : $("#no-items").show();

        const container = document.getElementById("items-table");
            container.innerHTML = "";

        const columnMapping = mapColumns(items);
        items.sort(function(a, b){
            for(let keyId in columnMapping){
                const aColumn = a.columns[keyId];
                const bColumn = b.columns[keyId];

                if(aColumn != undefined && bColumn == undefined){
                    return -1;
                }

                if(aColumn == undefined && bColumn != undefined){
                    return 1;
                }
            }

            return 0;
        });

        for(let iIndex in items){
            const item = items[iIndex];

            const row = document.createElement("TR");
                row.classList.add("item");

                const checkboxColumn = document.createElement("TD");
                    const checkbox = document.createElement("INPUT")
                        checkbox.type = "checkbox";
                checkboxColumn.appendChild(checkbox);
            row.appendChild(checkboxColumn);

                for(let keyId in columnMapping){
                    const valueColumn = document.createElement("TD");
                        const columnName = document.createElement("DIV");
                            columnName.classList.add("column-name");
                            columnName.innerHTML = keyCache.get(keyId);
                    valueColumn.appendChild(columnName);

                    if(item.columns[keyId] != undefined){
                        const value = document.createElement("TEXTAREA");
                            value.value = item.columns[keyId];
                            value.readOnly = true;
                            value.style.width = "90%";
                        valueColumn.appendChild(value);
                    }

                    row.appendChild(valueColumn);
                }

                const labelColumn = document.createElement("TD");
                    for(let lIndex in item.labelIds){
                        const labelId = item.labelIds[lIndex];

                        const label = document.createElement("SPAN");
                            label.classList.add("label");
                            label.innerHTML = labelCache.get(labelId);
                        labelColumn.appendChild(label);
                    }
            row.appendChild(labelColumn);
            container.appendChild(row);
        }

        function mapColumns(items){
            const result = {};

            for(let iIndex in items){
                const item = items[iIndex];
                for(let keyId in item.columns){
                    if(!result[keyId]){
                        result[keyId] = 0;
                    }

                    result[keyId]++;
                }
            }
            return orderMapByProperty(result, function(a, b){return b.getValue() - a.getValue()});
        };
    }

    function init(){
        loadItems();
    }

    function loadLabel(labelId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.GET_LABEL, labelId));

        return JSON.parse(response.body).labelValue;
    }

    function loadKey(keyId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.GET_KEY, keyId));
        return JSON.parse(response.body).keyValue;
    }
})();