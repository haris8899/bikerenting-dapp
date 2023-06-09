package com.example.bikerentingdapp.ContractManagement;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class Rental_sol_BikeRenting extends Contract {
    public static final String BINARY = "60806040525f80555f6001555f60025534801561001a575f80fd5b50610e2e806100285f395ff3fe608060405260043610610084575f3560e01c80636a3bea64116100575780636a3bea64146100ec5780639920505a14610101578063c29c86ee14610120578063d670e61614610154578063e5424f5114610173575f80fd5b806303742de0146100885780632ddbd13a1461009d5780633a4d076c146100c557806347eba6f2146100d8575b5f80fd5b61009b610096366004610a38565b6101a8565b005b3480156100a8575f80fd5b506100b260025481565b6040519081526020015b60405180910390f35b61009b6100d3366004610a38565b61051f565b3480156100e3575f80fd5b506100b25f5481565b3480156100f7575f80fd5b506100b260015481565b34801561010c575f80fd5b5061009b61011b366004610a63565b6106b2565b34801561012b575f80fd5b5061013f61013a366004610a38565b6107be565b6040516100bc99989796959493929190610b63565b34801561015f575f80fd5b506100b261016e366004610a38565b61089d565b34801561017e575f80fd5b5061019261018d366004610a38565b610956565b6040516100bc9a99989796959493929190610bc1565b5f818152600360205260409020600701548190336001600160a01b03909116036102295760405162461bcd60e51b815260206004820152602760248201527f4163636573732064656e696564206f6e6c792072656e7465722063616e20616360448201526618d95cdcc81a5d60ca1b60648201526084015b60405180910390fd5b5f8281526003602052604090206005015482906102469080610c3c565b3410156102955760405162461bcd60e51b815260206004820152601760248201527f42616c616e636520697320496e73756666696369656e740000000000000000006044820152606401610220565b5f83815260036020526040902060040154839060ff1615156001146103075760405162461bcd60e51b815260206004820152602260248201527f42696b652069732063757272656e746c79206e6f7420617661696c61626c652e604482015261171760f11b6064820152608401610220565b33610310575f80fd5b5f84815260036020526040812060070154600180546001600160a01b039092169261033a83610c55565b90915550505f8581526003602081815260408084206008810180546001600160a01b0319163317905560048101805460ff191690554281850155600180546006830155825161014081019093528a8352948a9052928252929091018054918301916103a490610c6d565b80601f01602080910402602001604051908101604052809291908181526020018280546103d090610c6d565b801561041b5780601f106103f25761010080835404028352916020019161041b565b820191905f5260205f20905b8154815290600101906020018083116103fe57829003601f168201915b50505091835250505f87815260036020908152604080832060058101548386015260020154818501524260608501526080840183905260a084018390526001805460c086018190523360e08701526001600160a01b03881661010090960195909552938352600482529091208351815590830151909182019061049e9082610cf3565b5060408201516002820155606082015160038201556080820151600482015560a0820151600582015560c0820151600682015560e082015160078201556101008201516008820180546001600160a01b03199081166001600160a01b0393841617909155610120909301516009909201805490931691161790555050505050565b5f8181526003602052604090206008015481906001600160a01b031633146105985760405162461bcd60e51b815260206004820152602660248201527f4e6f2061677265656d656e7420666f756e64206265747765656e20746865207060448201526561727469657360d01b6064820152608401610220565b5f6105a28361089d565b90508034101561060b5760405162461bcd60e51b815260206004820152602e60248201527f496e73756666696369656e742042616c616e636520506c6561736520746f707560448201526d1c081e5bdd5c881858d8dbdd5b9d60921b6064820152608401610220565b5f83815260046020526040808220600981015460089091015491516001600160a01b0391821693919092169183916108fc861502918691818181858888f1935050505015801561065d573d5f803e3d5ffd5b506040516001600160a01b038216904780156108fc02915f818181858888f19350505050158015610690573d5f803e3d5ffd5b5050505f9283525050600360205260409020600401805460ff19166001179055565b336106bb575f80fd5b5f805490806106c983610c55565b909155505060408051610120810182525f805480835260208084018881528486018790526060850184905260016080860181905260a0860189905260c086018590523360e087015261010086018590529284526003909152939091208251815592519192919082019061073c9082610cf3565b506040820151600282015560608201516003820155608082015160048201805460ff191691151591909117905560a0820151600582015560c0820151600682015560e08201516007820180546001600160a01b03199081166001600160a01b039384161790915561010090930151600890920180549093169116179055505050565b60036020525f9081526040902080546001820180549192916107df90610c6d565b80601f016020809104026020016040519081016040528092919081815260200182805461080b90610c6d565b80156108565780601f1061082d57610100808354040283529160200191610856565b820191905f5260205f20905b81548152906001019060200180831161083957829003601f168201915b505050506002830154600384015460048501546005860154600687015460078801546008909801549697949693955060ff9092169390926001600160a01b03918216911689565b5f81815260046020526040812060050154156108fb5760405162461bcd60e51b815260206004820152601760248201527f4e6f2041677265656d656e7420627920746869732069640000000000000000006044820152606401610220565b5f8281526004602081905260408220426005820181905591015461091e91610daf565b5f848152600460205260408120600201549192509061093f610e1084610dc2565b6109499190610de1565b6002819055949350505050565b60046020525f90815260409020805460018201805491929161097790610c6d565b80601f01602080910402602001604051908101604052809291908181526020018280546109a390610c6d565b80156109ee5780601f106109c5576101008083540402835291602001916109ee565b820191905f5260205f20905b8154815290600101906020018083116109d157829003601f168201915b50505060028401546003850154600486015460058701546006880154600789015460088a01546009909a015498999598949750929550909390926001600160a01b0391821691168a565b5f60208284031215610a48575f80fd5b5035919050565b634e487b7160e01b5f52604160045260245ffd5b5f805f60608486031215610a75575f80fd5b833567ffffffffffffffff80821115610a8c575f80fd5b818601915086601f830112610a9f575f80fd5b813581811115610ab157610ab1610a4f565b604051601f8201601f19908116603f01168101908382118183101715610ad957610ad9610a4f565b81604052828152896020848701011115610af1575f80fd5b826020860160208301375f60208483010152809750505050505060208401359150604084013590509250925092565b5f81518084525f5b81811015610b4457602081850181015186830182015201610b28565b505f602082860101526020601f19601f83011685010191505092915050565b5f6101208b8352806020840152610b7c8184018c610b20565b604084019a909a5250506060810196909652931515608086015260a085019290925260c08401526001600160a01b0390811660e0840152166101009091015292915050565b5f6101408c8352806020840152610bda8184018d610b20565b604084019b909b5250506060810197909752608087019590955260a086019390935260c085019190915260e08401526001600160a01b03908116610100840152166101209091015292915050565b634e487b7160e01b5f52601160045260245ffd5b80820180821115610c4f57610c4f610c28565b92915050565b5f60018201610c6657610c66610c28565b5060010190565b600181811c90821680610c8157607f821691505b602082108103610c9f57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610cee575f81815260208120601f850160051c81016020861015610ccb5750805b601f850160051c820191505b81811015610cea57828155600101610cd7565b5050505b505050565b815167ffffffffffffffff811115610d0d57610d0d610a4f565b610d2181610d1b8454610c6d565b84610ca5565b602080601f831160018114610d54575f8415610d3d5750858301515b5f19600386901b1c1916600185901b178555610cea565b5f85815260208120601f198616915b82811015610d8257888601518255948401946001909101908401610d63565b5085821015610d9f57878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b81810381811115610c4f57610c4f610c28565b5f82610ddc57634e487b7160e01b5f52601260045260245ffd5b500490565b8082028115828204841417610c4f57610c4f610c2856fea26469706673582212200104ee0d50d7c8f7dc8219dde5790ce1a235961c52d56cd149e6c2a9585104ae64736f6c63430008140033";

    public static final String FUNC_AGREEMENTMAPPING = "AgreementMapping";

    public static final String FUNC_CALCULATERENT = "CalculateRent";

    public static final String FUNC_RETURNBIKE = "ReturnBike";

    public static final String FUNC_ADDBIKE = "addBike";

    public static final String FUNC_BIKE_MAPPING = "bike_mapping";

    public static final String FUNC_NO_OF_BIKE_AGREEMENTS = "no_of_bike_agreements";

    public static final String FUNC_NO_OF_BIKES = "no_of_bikes";

    public static final String FUNC_SIGNAGREEMENT = "signAgreement";

    public static final String FUNC_TOTAL = "total";

    @Deprecated
    protected Rental_sol_BikeRenting(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Rental_sol_BikeRenting(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Rental_sol_BikeRenting(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Rental_sol_BikeRenting(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>> AgreementMapping(BigInteger param0) {
        final Function function = new Function(FUNC_AGREEMENTMAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>>(function,
                new Callable<Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue(), 
                                (String) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> CalculateRent(BigInteger _id) {
        final Function function = new Function(
                FUNC_CALCULATERENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> ReturnBike(BigInteger _id, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_RETURNBIKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> addBike(String _reg, BigInteger _rent, BigInteger _advance) {
        final Function function = new Function(
                FUNC_ADDBIKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_reg), 
                new org.web3j.abi.datatypes.generated.Uint256(_rent), 
                new org.web3j.abi.datatypes.generated.Uint256(_advance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String>> bike_mapping(BigInteger param0) {
        final Function function = new Function(FUNC_BIKE_MAPPING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String>>(function,
                new Callable<Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String>>() {
                    @Override
                    public Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (String) results.get(7).getValue(), 
                                (String) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> no_of_bike_agreements() {
        final Function function = new Function(FUNC_NO_OF_BIKE_AGREEMENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> no_of_bikes() {
        final Function function = new Function(FUNC_NO_OF_BIKES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> signAgreement(BigInteger _index, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SIGNAGREEMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> total() {
        final Function function = new Function(FUNC_TOTAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Rental_sol_BikeRenting load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rental_sol_BikeRenting(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Rental_sol_BikeRenting load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rental_sol_BikeRenting(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Rental_sol_BikeRenting load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Rental_sol_BikeRenting(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Rental_sol_BikeRenting load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Rental_sol_BikeRenting(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Rental_sol_BikeRenting> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Rental_sol_BikeRenting.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Rental_sol_BikeRenting> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Rental_sol_BikeRenting.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Rental_sol_BikeRenting> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Rental_sol_BikeRenting.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Rental_sol_BikeRenting> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Rental_sol_BikeRenting.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
