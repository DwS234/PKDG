import { ArrayInput, Create, SimpleForm, SimpleFormIterator, TextInput } from "react-admin";

const WordCreate = (props: any) => (
  <Create {...props}>
    <SimpleForm>
      <TextInput fullWidth source="entry" />
      <TextInput fullWidth multiline source="definition" />
      <ArrayInput source="examples">
        <SimpleFormIterator >
          <TextInput fullWidth multiline label="" source="sentence" />
        </SimpleFormIterator>
      </ArrayInput>
    </SimpleForm>
  </Create>
);

export default WordCreate;
