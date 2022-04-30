import { Admin as ReactAdmin, Resource } from "react-admin";

import WithAuthDataProvider from "../../components/admin/with-auth-data-provider/WithAuthDataProvider";
import CustomLayout from "../../components/admin/custom-layout/CustomLayout";
import WordList from "../../components/admin/word-list/WordList";
import WordCreate from "../../components/admin/word-create/WordCreate";
import WordEdit from "../../components/admin/word-edit/WordEdit";

const Admin = () => {
  return (
    <ReactAdmin basename="/admin" dataProvider={WithAuthDataProvider} layout={CustomLayout}>
      <Resource name="words" list={WordList} create={WordCreate} edit={WordEdit} />
    </ReactAdmin>
  );
};

export default Admin;
