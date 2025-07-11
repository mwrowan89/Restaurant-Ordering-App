import "./Header.css";

const Header = () => {
  return (
    <header>
      <div className="container">
        <h1>
          <a href="/">Home</a>
        </h1>
        <nav>
          <ul>
            <li>
              <a href="/login">Log In</a>
            </li>
            <li>
              <a href="/register">Register</a>
            </li>

            <li>
              <a href="/orders">Orders</a>
            </li>

            <li>
              <a href="/cart">Cart</a>
            </li>
            <li>
              <a href="/checkout">Checkout</a>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;
