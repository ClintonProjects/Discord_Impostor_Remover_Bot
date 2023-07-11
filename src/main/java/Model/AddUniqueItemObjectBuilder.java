package Model;

public class AddUniqueItemObjectBuilder extends AddUnquieItemObject {
    
    public static class Builder {
        
        private AddUniqueItemObjectBuilder addUniqueItemObject;
        
        public Builder() {
            addUniqueItemObject = new AddUniqueItemObjectBuilder();
        }
        
        public Builder withDbName(String dbName) {
            addUniqueItemObject.setDbName(dbName);
            return this;
        }
        
        public Builder withObject(Object object) {
            addUniqueItemObject.setObject(object);
            return this;
        }
        
        public Builder withAccept(String accept) {
            addUniqueItemObject.setAccept(accept);
            return this;
        }
        
        public Builder withDeclined(String declined) {
            addUniqueItemObject.setDeclined(declined);
            return this;
        }
        
        public Builder withName(String name) {
            addUniqueItemObject.setName(name);
            return this;
        }
        
        public Builder withValue(String value) {
            addUniqueItemObject.setValue(value);
            return this;
        }
        
        public AddUniqueItemObjectBuilder build() {
            return addUniqueItemObject;
        }
    }
}
