window.Mapping = {
    GET_OBJECT_FROM_STORE: "/stored-object/*",
    SAVE_TO_OBJECT_STORE: "/stored-object",

    concat: function(path, id){
        return path.replace("*", id);
    }
}