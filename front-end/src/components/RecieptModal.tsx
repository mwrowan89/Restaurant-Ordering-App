// This component is not used in the current version of the app.
// It is a modal that displays the receipt of an order.

import { useNavigate } from "react-router-dom";
import "./RecieptModal.css";

interface Order {
  id: number;
  userid: number;
  ordertime: string;
  tax: number;
  tip: number;
  total: number;
  status: string;
}

interface ReceiptProps {
  order: Order;
  onClose: () => void;
}

const Receipt = ({ order, onClose }: ReceiptProps) => {
  const navigate = useNavigate();

  const handleClose = () => {
    onClose();
    navigate("/");
  };

  return (
    <div className="receipt-modal">
      <div className="receipt-content">
        <button className="close-button" onClick={handleClose}>
          Close
        </button>
        <h1>Receipt</h1>
        <p>Order ID: {order.id}</p>
        <p>Order Time: {new Date(order.ordertime).toLocaleString()}</p>
        <p>Status: {order.status}</p>
        {/* //TODO not working */}
        {/* <p>Tax: ${order.tax.toFixed(2)}</p>
        <p>Tip: ${order.tip.toFixed(2)}</p>
        <p>Total: ${(order.tax + order.tip).toFixed(2)}</p> */}
      </div>
    </div>
  );
};

export default Receipt;
