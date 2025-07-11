import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Orders.css";

interface Order {
  id: number;
  userid: number;
  ordertime: string;
  tax: number;
  tip: number;
  total: number;
  status: string;
}

const Orders = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await axios.get<Order[]>("/api/orders");
        setOrders(response.data);
        setErrorMessage(null);
      } catch {
        setErrorMessage(
          "An error occurred while fetching the orders. Please try again later."
        );
      }
    };

    fetchOrders();
  }, []);

  const handleRowClick = (orderId: number) => {
    navigate(`/orders/${orderId}`);
  };

  return (
    <div className="orders-container">
      <h1 className="orders-title">All Orders</h1>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {orders.length > 0 ? (
        <div className="table-container">
          <table className="orders-table">
            <thead>
              <tr>
                <th>Order ID</th>
                <th>Order Time</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {[...orders].reverse().map((order) => (
                <tr
                  key={order.id}
                  className="table-row"
                  onClick={() => handleRowClick(order.id)}
                >
                  <td>{order.id}</td>
                  <td>{new Date(order.ordertime).toLocaleString()}</td>
                  <td>{order.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <p className="no-orders">No orders found.</p>
      )}
    </div>
  );
};

export default Orders;
