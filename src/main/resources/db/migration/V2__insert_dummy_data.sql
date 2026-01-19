-- Products
INSERT INTO products (id, name, base_price, country)
VALUES
  ('p1', 'iPhone 15', 900.00, 'Sweden'),
  ('p2', 'MacBook Pro', 2400.00, 'Germany'),
  ('p3', 'AirPods Pro', 250.00, 'France');

-- Discounts (optional, created automatically when applied)
INSERT INTO discounts (discount_id, percent)
VALUES
  ('WELCOME10', 10.00),
  ('NEWYEAR5', 5.00)
ON CONFLICT (discount_id) DO NOTHING;

-- Pre-applied discount (optional)
INSERT INTO product_discounts (product_id, discount_id)
VALUES ('p1', 'WELCOME10')
ON CONFLICT DO NOTHING;