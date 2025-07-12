export interface CartItem {
  id: string;
  name: string;
  description: string;
  category: string;
  price: number;
  imageurl: string;
  available: boolean;
  quantity: number;
  updateCartItemQuantity: (id: string, quantity: number) => void;
}

export interface CartContextType {
  cart: CartItem[];
  addToCart: (item: CartItem) => void;
  removeFromCart: (id: string) => void;
  clearCart: () => void;
  getCartTotal: () => number;
  updateCartItemQuantity: (id: string, quantity: number) => void;
}