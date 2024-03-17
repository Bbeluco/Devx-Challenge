// eslint-disable-next-line @typescript-eslint/no-unused-vars
import styles from './app.module.scss';


import { Route, Routes, Link } from 'react-router-dom';
import Login from '../components/login/login';

export function App() {
  return (
    <div>
      <Routes>
        <Route
          path="/"
          element={
            <Login />
          }
        />
        <Route
          path="/page-2"
          element={
            <div>
              <Link to="/">Click here to go back to root page.</Link>
            </div>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
