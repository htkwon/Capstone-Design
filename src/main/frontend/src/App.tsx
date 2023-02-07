import React, { useEffect, useState } from "react";
import axios from "axios";
import Home from "./components/pages/Home";
import BoardWrite from "./components/pages/BoardWritePage"; //추후 커밋 할 때 삭제할 것

function App() {
  const [test, setTest] = useState(""); // 연동 테스트용 state

  useEffect(() => {
    axios
      .get("/api/test")
      .then((response) => setTest(response.data))
      .catch((error) => console.log(error));
  }, []);

  // fetch
  /*
  useEffect(() => {
    fetch("/api/test")
      .then((response) => response.text())
      .then(setTest);
  }, []);
  */

  return (
    <>
    <div>프론트엔드 백엔드 연동 테스트 : {test}</div>
    {/*<Home/>*/}
    <BoardWrite/>
    </>
  )
}

export default App;
