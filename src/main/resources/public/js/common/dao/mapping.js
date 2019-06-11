window.Mapping = {
    CREATE_ITEM: "/item",
    GET_ITEMS: "/item",
    GET_KEY: "/key/*",
    GET_KEYS: "/key",
    GET_LABEL: "/label/*",
    GET_LABELS: "/label",
    GET_OBJECT_FROM_STORE: "/stored-object/*",
    SAVE_TO_OBJECT_STORE: "/stored-object",

    concat: function(path, id){
        return path.replace("*", id);
    }
}