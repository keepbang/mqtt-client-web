import './App.css';
import Header from "./component/section/Header";
import PublishArea from "./component/section/PublishArea";
import {useState} from "react";

function App() {
    const [connected, setConnected] = useState(false);

    return (
    <div className="App">
      <Header connected={connected} onConnected={setConnected}/>
        {
            connected ? <PublishArea/> : ''
        }
    </div>
  );
}

export default App;
