import { useState } from "react";
import { useCart } from "../context/CartContext";
import { useNavigate } from "react-router-dom";
import "./CartIcon.css";

const CartIcon = () => {
  const { cart, getCartTotal } = useCart();
  const navigate = useNavigate();
  const [isHovered, setIsHovered] = useState(false);

  const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <div
      className="cart-icon relative"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onClick={() => navigate("/cart")}
    >
      {/* Cart image from w3 */}
      <svg
        xmlns="http://www.w3.org/2000/svg"
        className="cart-icon-svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={2}
          d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2 5h14m-6-5v5m-4-5v5"
        />
      </svg>
      {/* Red icon with order total */}
      {totalItems > 0 && <span className="cart-icon-badge">{totalItems}</span>}
      {/* Cart dropdown display */}
      {isHovered && cart.length > 0 && (
        <div className="cart-dropdown">
          <ul className="cart-dropdown-list">
            {cart.map((item) => (
              <li key={item.id} className="cart-item-hover">
                <span>{item.name}</span>
                <span>x{item.quantity}</span>
              </li>
            ))}
          </ul>
          <div className="cart-icon-total">
            <span className="font-bold">Total:</span>
            <span className="text-lg font-semibold">
              ${getCartTotal().toFixed(2)}
            </span>
          </div>
        </div>
      )}
    </div>
  );
};

export default CartIcon;
