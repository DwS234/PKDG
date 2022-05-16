import { Datagrid, List, TextField } from "react-admin";

const WordList = () => {
  return (
    <List exporter={false}>
      <Datagrid rowClick="edit" bulkActionButtons={false}>
        <TextField source="id" />
        <TextField source="entry" />
        <TextField source="definition" />
      </Datagrid>
    </List>
  );
};

export default WordList;
