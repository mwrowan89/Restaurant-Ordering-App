import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./Orders.css";
import { useMenuItems } from "../context/MenuItemsContext";
import { MenuItem } from "../types/MenuItemType";

interface Order {
  id: number;
  userid: number;
  ordertime: string;
  tax: number;
  tip: number;
  total: number;
  status: string;
}

interface OrderItems {
  id: number;
  orderid: number;
  itemid: number;
  price: number;
  notes: string;
  firstname: string;
}

const OrderDetails = () => {
  const { orderId } = useParams<{ orderId: string }>();
  const { menuItems } = useMenuItems();
  const [order, setOrder] = useState<Order | null>(null);
  const [orderMenuItems, setOrderMenuItems] = useState<MenuItem[]>([]);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    const fetchOrderDetails = async () => {
      try {
        // Fetch order details by order ID
        const orderResponse = await axios.get<Order>(`/api/orders/${orderId}`);
        setOrder(orderResponse.data);

        // Fetch order items by order ID
        const orderItemsResponse = await axios.get<OrderItems[]>(
          `/api/items/order/${orderId}`
        );

        // Map order items from menu items
        const filteredMenuItems = orderItemsResponse.data
          .map((orderItem) =>
            menuItems.find((menuItem) => menuItem.id === orderItem.itemid)
          )
          .filter((item): item is MenuItem => item !== undefined);

        setOrderMenuItems(filteredMenuItems);
        setErrorMessage(null);
      } catch (error) {
        if (
          axios.isAxiosError(error) &&
          error.response &&
          error.response.status === 404
        ) {
          setErrorMessage(
            "Order not found. Please check the Order ID and try again."
          );
        } else {
          setErrorMessage(
            "An error occurred while fetching the order. Please try again later."
          );
        }
      }
    };

    if (orderId) {
      fetchOrderDetails();
    }
  }, [orderId, menuItems]);

  const orderTotal =
    orderMenuItems.reduce((sum, item) => sum + item.price, 0) +
    (order?.tax || 0) +
    (order?.tip || 0);
  if (errorMessage) {
    return <p className="text-red-500">{errorMessage}</p>;
  }

  if (!order) {
    return <p>Loading order details...</p>;
  }

  return (
    <div className="orders-container">
      <h1 className="orders-title">Order Details</h1>
      <p>
        <strong>Order #:</strong> {order.id}
      </p>
      <p>
        <strong>Order Time:</strong>{" "}
        {new Date(order.ordertime).toLocaleString()}
      </p>
      <p>
        <strong>Status:</strong> {order.status}
      </p>
      <br />
      <ul className="order-items-list">
        {orderMenuItems.map((item, index) => (
          <li key={index} className="order-item">
            <p>
              <strong>{item.name}</strong>
            </p>
            <p>
              <strong>Price:</strong> ${item.price.toFixed(2)}
            </p>
          </li>
        ))}
      </ul>
      <br />
      <hr />
      <br />
      <div className="order-price-summary">
        <p>Tax: ${order.tax.toFixed(2)}</p>
        <p>Tip: ${order.tip.toFixed(2)}</p>
        <p id="order-total">
          <strong>Total:</strong> ${orderTotal.toFixed(2)}
        </p>
      </div>
    </div>
  );
};

export default OrderDetails;
