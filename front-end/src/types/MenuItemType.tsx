export interface MenuItem {
  id: string;
  name: string;
  description: string;
  category: string;
  price: number;
  imageurl: string;
  available: boolean;
}

export interface MenuItemsContextType {
  menuItems: MenuItem[];
}