import { useState } from "react";
import { useCart } from "../context/CartContext";
import { useMenuItems } from "../context/MenuItemsContext";
import "./MenuList.css";
import { MenuItem } from "../types/MenuItemType";

const MenuList = () => {
  const { menuItems } = useMenuItems();
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<string>("all");
  const { addToCart } = useCart();

  const handleAddToCart = (item: MenuItem) => {
    addToCart({
      id: item.id,
      description: item.description,
      category: item.category,
      available: item.available,
      imageurl: item.imageurl,
      name: item.name,
      price: item.price,
      quantity: 1,
      updateCartItemQuantity: (id: number, quantity: number) => {
        console.log(`Updating item ${id} quantity to ${quantity}`);
      },
    });
    setSuccessMessage(`${item.name} has been added to the cart!`);
    setTimeout(() => setSuccessMessage(null), 3000);
  };

  //Filter menu items based on category
  const filteredMenuItems =
    selectedCategory === "all"
      ? menuItems
      : menuItems.filter((item) => item.category === selectedCategory);

  return (
    <div className="menu-list-container">
      {successMessage && (
        <div className="success-message">{successMessage}</div>
      )}

      <h1 className="menu-title">Menu</h1>

      <div className="tabs">
        {["all", "entrees", "sushi", "pho","desserts"].map((category) => (
          <button
            key={category}
            onClick={() => setSelectedCategory(category)}
            className={`tab-button ${
              selectedCategory === category ? "active" : ""
            }`}
          >
            {category.charAt(0).toUpperCase() + category.slice(1)}
          </button>
        ))}
      </div>

      <div className="menu-grid">
        {filteredMenuItems.map((item) => (
          <div key={item.id} className="menu-item">
            <h2 className="menu-item-title">{item.name}</h2>
            <img
              src={`src${item.imageurl}`}
              alt={item.name}
              className="menu-item-image"
            />
            <p className="menu-item-description">{item.description}</p>
            <div className="menu-item-price-button">
              <p className="menu-item-price">Price: ${item.price.toFixed(2)}</p>
              <button
                onClick={() => handleAddToCart(item)}
                className="menu-item-button"
              >
                Add to Cart
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MenuList;
