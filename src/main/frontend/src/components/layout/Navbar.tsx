import React from "react";
import { NavLink } from "react-router-dom";
import { menuItems } from "../data/MenuItems";
import { RxAvatar } from "react-icons/rx";
import "../style/Home.css";

const Navbar: React.FC = () => {
    
    const goToMyPage = () => {

    }

    return (
        <div>
            <nav className="navigation">
            <ul>
            {menuItems.map((menu, index) => {
                return (
                    <li>
                        <NavLink to={menu.url} 
                        style={({ isActive }) => ({
                            color: isActive ? "black" : "black",
                            textDecoration: "none",
                        })}>{menu.title}</NavLink>
                    </li>
                );
            })}
            </ul>
            <NavLink to="/mypage" style={({ isActive }) => ({
                            color: isActive ? "black" : "black",
                            textDecoration: "none",
                        })}>
                <RxAvatar size={35} className="myPageIcon" onClick={goToMyPage}/>
            </NavLink>
            </nav>
        </div>
    );
  }
  
  export default Navbar;