import { useState } from "react";
import {
    ShieldCheck,
    Network,
    Database,
    LoaderCircle,
    CheckCircle,
    XCircle
} from "lucide-react";
import api from "../services/api";

function Verification() {

    const [result, setResult] = useState(null);

    const [loading, setLoading] = useState(false);

    const verifyGraph = async () => {

        try {

            setLoading(true);

            setResult(null);

            const response = await api.get("/verify");

            const data = response.data;

            const nodes =
                    data.match(
                            /Nodes\s*:\s*(\d+)/
                    );

            const relationships =
                    data.match(
                            /Relationships\s*:\s*(\d+)/
                    );

            setResult({

                verified: true,

                nodes:
                        nodes
                        ? nodes[1]
                        : "0",

                relationships:
                        relationships
                        ? relationships[1]
                        : "0",

                raw: data,

                message:
                        "Graph verification completed successfully."

            });

        }

        catch (error) {

            setResult({

                verified: false,

                nodes: "0",

                relationships: "0",

                raw: "",

                message:
                        "Unable to verify graph."

            });

        }

        finally {

            setLoading(false);

        }

    };

    return (

        <div className="min-h-screen bg-gray-50 p-6">

            <div className="max-w-5xl mx-auto">

                <h1 className="text-3xl font-bold mb-6">

                    Graph Verification

                </h1>

                <div className="bg-white shadow-lg rounded-xl p-6">

                    <div className="flex items-center gap-3 mb-6">

                        <ShieldCheck
                            className="text-purple-600"
                            size={36}
                        />

                        <div>

                            <h2 className="text-xl font-semibold">

                                Verify Graph Structure

                            </h2>

                            <p className="text-gray-500 text-sm">

                                Verify total nodes and relationships stored in the graph database.

                            </p>

                        </div>

                    </div>

                    <button
                        onClick={verifyGraph}
                        disabled={loading}
                        className="
                        bg-purple-600
                        hover:bg-purple-700
                        disabled:bg-gray-400
                        text-white
                        px-6
                        py-2
                        rounded-lg
                        flex
                        items-center
                        gap-2
                        transition
                        "
                    >

                        {
                            loading &&

                            <LoaderCircle
                                className="animate-spin"
                                size={18}
                            />

                        }

                        {
                            loading
                            ? "Verifying..."
                            : "Verify Graph"
                        }

                    </button>
                    {
                        result &&

                        <div className="mt-6">

                            <div
                                className={`
                                rounded-xl
                                border
                                p-5
                                mb-6
                                ${
                                    result.verified
                                    ? "border-green-300 bg-green-50"
                                    : "border-red-300 bg-red-50"
                                }
                                `}
                            >

                                <div className="flex items-center gap-3 mb-2">

                                    {
                                        result.verified
                                        ?

                                        <CheckCircle
                                            className="text-green-600"
                                            size={28}
                                        />

                                        :

                                        <XCircle
                                            className="text-red-600"
                                            size={28}
                                        />

                                    }

                                    <h3 className="text-lg font-semibold">

                                        {
                                            result.verified
                                            ? "Graph Verified Successfully"
                                            : "Graph Verification Failed"
                                        }

                                    </h3>

                                </div>

                                <p className="text-gray-700">

                                    {result.message}

                                </p>

                            </div>





                            <div className="grid grid-cols-1 md:grid-cols-2 gap-5">

                                <div
                                    className="
                                    bg-blue-50
                                    rounded-xl
                                    border
                                    p-6
                                    flex
                                    items-center
                                    gap-4
                                    "
                                >

                                    <Database
                                        className="text-blue-600"
                                        size={38}
                                    />

                                    <div>

                                        <p className="text-gray-500">

                                            Total Nodes

                                        </p>

                                        <h2 className="text-3xl font-bold text-blue-700">

                                            {result.nodes}

                                        </h2>

                                    </div>

                                </div>





                                <div
                                    className="
                                    bg-green-50
                                    rounded-xl
                                    border
                                    p-6
                                    flex
                                    items-center
                                    gap-4
                                    "
                                >

                                    <Network
                                        className="text-green-600"
                                        size={38}
                                    />

                                    <div>

                                        <p className="text-gray-500">

                                            Total Relationships

                                        </p>

                                        <h2 className="text-3xl font-bold text-green-700">

                                            {result.relationships}

                                        </h2>

                                    </div>

                                </div>

                            </div>





                            <div className="mt-6">

                                <h3 className="font-semibold mb-3">

                                    Backend Response

                                </h3>

                                <div
                                    className="
                                    bg-gray-900
                                    text-green-400
                                    rounded-xl
                                    p-5
                                    overflow-x-auto
                                    "
                                >

                                    <pre className="whitespace-pre-wrap text-sm">

                                        {result.raw}

                                    </pre>

                                </div>

                            </div>

                        </div>

                    }

                </div>

            </div>

        </div>

    );

}

export default Verification;