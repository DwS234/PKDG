import { Datagrid, List, TextField } from "react-admin";

const WordList = () => {
  return (
    <List>
      <Datagrid rowClick="edit">
        <TextField source="id" />
        <TextField source="entry" />
        <TextField source="definition" />
      </Datagrid>
    </List>
  );
};

export default WordList;
