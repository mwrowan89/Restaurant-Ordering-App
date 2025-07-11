import { useCart } from "../context/CartContext";
import { useNavigate } from "react-router-dom";
import "./Cart.css";

const Cart = () => {
  const {
    cart,
    removeFromCart,
    clearCart,
    getCartTotal,
    updateCartItemQuantity,
  } = useCart();
  const navigate = useNavigate();

  return (
    <div className="cart-container">
      <h2 className="cart-title">Cart</h2>
      {cart.length === 0 ? (
        <p className="empty-cart-message">Your cart is empty.</p>
      ) : (
      <div className="cart-item-container">
        {cart.map((item) => (
          <div key={item.id} className="cart-item">
            <h3 className="cart-item-title">{item.name}</h3>
            <img
              src={`src${item.imageurl}`}
              alt={item.name}
              className="cart-item-image"
            />
            <p className="cart-item-price">
              <select
                value={item.quantity}
                onChange={(e) =>
                  updateCartItemQuantity(item.id, parseInt(e.target.value))
                }
                className="cart-item-quantity-dropdown"
              >
                {Array.from({ length: 10 }, (_, i) => i + 1).map((value) => (
                  <option key={value} value={value}>
                    {value}
                  </option>
                ))}
              </select>
              &nbsp;&nbsp; x &nbsp;&nbsp;${item.price.toFixed(2)}
            </p>
            <button
              onClick={() => removeFromCart(item.id)}
              className="cart-item-remove"
            >
              Remove
            </button>
          </div>
        ))}
      </div>
      )}

      <div className="cart-total">
        <h2 className="cart-total-title">Total:</h2>
        <span className="cart-total-amount">${getCartTotal().toFixed(2)}</span>
      </div>
      <div className="cart-action-btns">
        <button onClick={clearCart} className="cart-clear-btn">
          Clear Cart
        </button>
        <button
          onClick={() => navigate("/checkout")}
          className="cart-checkout-btn"
          disabled={cart.length === 0}
        >
          Checkout
        </button>
      </div>
      
    </div>
        
  );
};

export default Cart;
