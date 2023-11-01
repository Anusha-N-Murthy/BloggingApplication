import { myAxios } from "./helper";

export const loadAllCategories = () => {
  return myAxios.get(`category/`).then((respone) => {
    return respone.data;
  });
};
