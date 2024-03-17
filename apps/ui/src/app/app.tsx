// eslint-disable-next-line @typescript-eslint/no-unused-vars
import styles from './app.module.scss';


import { Route, Routes } from 'react-router-dom';
import Login from '../components/login/login';
import LoggedPage from '../components/loggedPage/loggedPage';
import { useState } from 'react';

export function App() {

  const [jwtToken, setJwtToken] = useState("");

  function handlerJwtToken(token: string) {
    setJwtToken(token);
  } 

  return (
    <div className={styles.main}>
      <Routes>
        <Route
          path="/"
          element={
            <Login handlerJwtToken={handlerJwtToken}/>
          }
        />
        <Route
          path="/loggedPage"
          element={
            <LoggedPage jwtToken={jwtToken}/>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
