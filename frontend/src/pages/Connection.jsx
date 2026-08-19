import { useState } from "react";
import {
    Database,
    CheckCircle,
    XCircle,
    Server,
    Activity
} from "lucide-react";
import api from "../services/api";

function Connection() {

    const [database, setDatabase] = useState("COGNODB");
    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(false);

    const connectDatabase = async () => {

        try {

            setLoading(true);

            // Select Database
            await api.post(`/database/select/${database}`);

            // Connect
            const response =
                await api.get("/benchmark/connect");

            setResult(response.data);

        }
        catch (error) {

            setResult({
                connected: false,
                databaseName: database,
                message: "Unable to connect."
            });

        }
        finally {

            setLoading(false);

        }

    };

    return (

        <div className="min-h-screen bg-gray-100 p-6">

            <div className="max-w-5xl mx-auto">

                <div className="bg-white rounded-xl shadow-lg p-6">

                    <div className="flex items-center gap-3 mb-6">

                        <Database
                            size={36}
                            className="text-blue-600"
                        />

                        <div>

                            <h1 className="text-3xl font-bold">
                                Database Connection
                            </h1>

                            <p className="text-gray-500">
                                Connect Graph Database for Benchmarking
                            </p>

                        </div>

                    </div>

                    <div className="grid md:grid-cols-2 gap-6">

                        <div>

                            <label className="block font-semibold mb-2">
                                Select Database
                            </label>

                            <select
                                value={database}
                                onChange={(e) =>
                                    setDatabase(e.target.value)
                                }
                                className="w-full border rounded-lg p-3"
                            >

                                <option value="COGNODB">
                                    CognoDB
                                </option>

                                <option value="NEO4J">
                                    Neo4j
                                </option>

                                <option value="MEMGRAPH">
                                    Memgraph
                                </option>

                                <option value="FALKORDB">
                                    FalkorDB
                                </option>

                            </select>

                        </div>

                        <div className="flex items-end">

                            <button
                                onClick={connectDatabase}
                                disabled={loading}
                                className="
                                w-full
                                bg-blue-600
                                hover:bg-blue-700
                                text-white
                                rounded-lg
                                py-3
                                font-semibold
                                disabled:bg-gray-400
                                "
                            >

                                {
                                    loading
                                        ? "Connecting..."
                                        : "Connect Database"
                                }

                            </button>

                        </div>

                    </div>

                </div>

                {result && (

                    <div className="mt-6 bg-white rounded-xl shadow-lg p-6">

                        <div className="flex items-center gap-3 mb-5">

                            {
                                result.connected
                                    ?

                                    <CheckCircle
                                        className="text-green-600"
                                        size={30}
                                    />

                                    :

                                    <XCircle
                                        className="text-red-600"
                                        size={30}
                                    />
                            }

                            <h2 className="text-2xl font-bold">

                                {
                                    result.connected
                                        ? "Connection Successful"
                                        : "Connection Failed"
                                }

                            </h2>

                        </div>

                        <div className="grid md:grid-cols-2 gap-5">

                            <div className="border rounded-lg p-4">

                                <div className="flex items-center gap-2 mb-2">

                                    <Server
                                        className="text-blue-600"
                                        size={20}
                                    />

                                    <strong>
                                        Database
                                    </strong>

                                </div>

                                <p>
                                    {result.databaseName}
                                </p>

                            </div>

                            <div className="border rounded-lg p-4">

                                <div className="flex items-center gap-2 mb-2">

                                    <Activity
                                        className="text-green-600"
                                        size={20}
                                    />

                                    <strong>
                                        Status
                                    </strong>

                                </div>

                                <p
                                    className={
                                        result.connected
                                            ? "text-green-600 font-semibold"
                                            : "text-red-600 font-semibold"
                                    }
                                >

                                    {
                                        result.connected
                                            ? "Connected"
                                            : "Failed"
                                    }

                                </p>

                            </div>

                            <div className="md:col-span-2 border rounded-lg p-4">

                                <strong>
                                    Server Message
                                </strong>

                                <p className="mt-2 text-gray-700">

                                    {result.message}

                                </p>

                            </div>

                        </div>

                    </div>

                )}

            </div>

        </div>

    );

}

export default Connection;