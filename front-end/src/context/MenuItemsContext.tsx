import {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from "react";
import axios from "axios";
import { MenuItem, MenuItemsContextType } from "../types/MenuItemType";


const MenuItemsContext = createContext<MenuItemsContextType | undefined>(
  undefined
);

export const MenuItemsProvider = ({ children }: { children: ReactNode }) => {
  const [menuItems, setMenuItems] = useState<MenuItem[]>([]);

  useEffect(() => {
    const fetchMenuItems = async () => {
      try {
        const response = await axios.get<MenuItem[]>("/api/menuitems");
        setMenuItems(response.data);
      } catch (err) {
        console.error("Error fetching menu items:", err);
        alert("Failed to fetch menu items.");
      }
    };

    fetchMenuItems();
  }, []);

  return (
    <MenuItemsContext.Provider value={{ menuItems }}>
      {children}
    </MenuItemsContext.Provider>
  );
};

// eslint-disable-next-line
export const useMenuItems = () => {
  const context = useContext(MenuItemsContext);
  if (!context) {
    throw new Error("useMenuItems must be used within a MenuItemsProvider");
  }
  return context;
};
