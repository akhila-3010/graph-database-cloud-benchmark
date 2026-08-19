import { useState } from "react";
import { NavLink } from "react-router-dom";
import { Menu, X } from "lucide-react";


function Navbar() {


    const [open, setOpen] = useState(false);



    const linkStyle = ({isActive}) =>

        isActive

        ?

        "font-bold underline"

        :

        "hover:underline";



    const closeMenu = () => {

        setOpen(false);

    };



    return (


        <>


            <nav className="
                fixed
                top-0
                left-0
                w-full
                bg-blue-600
                text-white
                z-50
                shadow-lg
            ">


                <div className="
                    max-w-7xl
                    mx-auto
                    flex
                    items-center
                    justify-between
                    p-4
                ">


                    <h1 className="
                        text-xl
                        font-bold
                    ">

                        Graph DB Benchmark

                    </h1>



                    {/* Desktop Menu */}

                    <div className="
                        hidden
                        md:flex
                        gap-5
                    ">


                        <NavLink 
                            to="/"
                            className={linkStyle}
                        >

                            Home

                        </NavLink>


                        <NavLink 
                            to="/connection"
                            className={linkStyle}
                        >

                            Connection

                        </NavLink>


                        <NavLink 
                            to="/dataset"
                            className={linkStyle}
                        >

                            Dataset

                        </NavLink>


                        <NavLink 
                            to="/verification"
                            className={linkStyle}
                        >

                            Verification

                        </NavLink>


                        <NavLink 
                            to="/benchmark"
                            className={linkStyle}
                        >

                            Benchmark

                        </NavLink>


                        <NavLink 
                            to="/report"
                            className={linkStyle}
                        >

                            Report

                        </NavLink>


                    </div>




                    {/* Mobile Button */}


                    <button

                        className="
                            md:hidden
                        "

                        onClick={() => setOpen(true)}

                    >

                        <Menu size={30}/>

                    </button>



                </div>


            </nav>





            {/* Mobile Overlay */}


            {
                open &&


                <div

                    className="
                        fixed
                        inset-0
                        bg-transparent
                        z-40
                    "

                    onClick={closeMenu}

                />


            }





            {/* Mobile Offcanvas */}


            <div

                className={`
                    fixed
                    top-0
                    right-0
                    h-full
                    w-72
                    bg-blue-600
                    text-white
                    z-50
                    transform
                    transition-transform
                    duration-300
                    ${
                        open
                        ?
                        "translate-x-0"
                        :
                        "translate-x-full"
                    }
                `}

            >



                <div className="
                    flex
                    justify-between
                    items-center
                    p-5
                    border-b
                    border-blue-400
                ">


                    <h2 className="font-bold text-lg">

                        Menu

                    </h2>



                    <button

                        onClick={closeMenu}

                    >

                        <X size={28}/>

                    </button>


                </div>





                <div className="
                    flex
                    flex-col
                    gap-5
                    p-6
                ">



                    <NavLink
                        onClick={closeMenu}
                        to="/"
                        className={linkStyle}
                    >

                        Home

                    </NavLink>



                    <NavLink
                        onClick={closeMenu}
                        to="/connection"
                        className={linkStyle}
                    >

                        Connection

                    </NavLink>



                    <NavLink
                        onClick={closeMenu}
                        to="/dataset"
                        className={linkStyle}
                    >

                        Dataset

                    </NavLink>



                    <NavLink
                        onClick={closeMenu}
                        to="/verification"
                        className={linkStyle}
                    >

                        Verification

                    </NavLink>



                    <NavLink
                        onClick={closeMenu}
                        to="/benchmark"
                        className={linkStyle}
                    >

                        Benchmark

                    </NavLink>



                    <NavLink
                        onClick={closeMenu}
                        to="/report"
                        className={linkStyle}
                    >

                        Report

                    </NavLink>


                </div>



            </div>


        </>

    );

}


export default Navbar;