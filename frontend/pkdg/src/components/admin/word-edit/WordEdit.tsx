import { ArrayInput, Edit, SimpleForm, SimpleFormIterator, TextInput } from "react-admin";

const WordEdit = (props: any) => (
  <Edit {...props}>
    <SimpleForm>
      <TextInput fullWidth disabled label="id" source="id" />
      <TextInput fullWidth source="entry" />
      <TextInput fullWidth multiline source="definition" />
      <ArrayInput source="examples">
        <SimpleFormIterator>
          <TextInput fullWidth multiline label="" source="sentence" />
        </SimpleFormIterator>
      </ArrayInput>
    </SimpleForm>
  </Edit>
);

export default WordEdit;
