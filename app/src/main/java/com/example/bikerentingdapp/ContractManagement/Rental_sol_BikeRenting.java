package com.example.bikerentingdapp;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
    public static final String BINARY = "60806040525f80555f6001555f60025534801561001a575f80fd5b50610e67806100285f395ff3fe608060405260043610610084575f3560e01c80636a3bea64116100575780636a3bea64146100ec5780639920505a14610101578063c29c86ee14610120578063d670e61614610154578063e5424f5114610173575f80fd5b806303742de0146100885780632ddbd13a1461009d5780633a4d076c146100c557806347eba6f2146100d8575b5f80fd5b61009b610096366004610a71565b6101a8565b005b3480156100a8575f80fd5b506100b260025481565b6040519081526020015b60405180910390f35b61009b6100d3366004610a71565b61051f565b3480156100e3575f80fd5b506100b25f5481565b3480156100f7575f80fd5b506100b260015481565b34801561010c575f80fd5b5061009b61011b366004610a9c565b6106eb565b34801561012b575f80fd5b5061013f61013a366004610a71565b6107f7565b6040516100bc99989796959493929190610b9c565b34801561015f575f80fd5b506100b261016e366004610a71565b6108d6565b34801561017e575f80fd5b5061019261018d366004610a71565b61098f565b6040516100bc9a99989796959493929190610bfa565b5f818152600360205260409020600701548190336001600160a01b03909116036102295760405162461bcd60e51b815260206004820152602760248201527f4163636573732064656e696564206f6e6c792072656e7465722063616e20616360448201526618d95cdcc81a5d60ca1b60648201526084015b60405180910390fd5b5f8281526003602052604090206005015482906102469080610c75565b3410156102955760405162461bcd60e51b815260206004820152601760248201527f42616c616e636520697320496e73756666696369656e740000000000000000006044820152606401610220565b5f83815260036020526040902060040154839060ff1615156001146103075760405162461bcd60e51b815260206004820152602260248201527f42696b652069732063757272656e746c79206e6f7420617661696c61626c652e604482015261171760f11b6064820152608401610220565b33610310575f80fd5b5f84815260036020526040812060070154600180546001600160a01b039092169261033a83610c8e565b90915550505f8581526003602081815260408084206008810180546001600160a01b0319163317905560048101805460ff191690554281850155600180546006830155825161014081019093528a8352948a9052928252929091018054918301916103a490610ca6565b80601f01602080910402602001604051908101604052809291908181526020018280546103d090610ca6565b801561041b5780601f106103f25761010080835404028352916020019161041b565b820191905f5260205f20905b8154815290600101906020018083116103fe57829003601f168201915b50505091835250505f87815260036020908152604080832060058101548386015260020154818501524260608501526080840183905260a084018390526001805460c086018190523360e08701526001600160a01b03881661010090960195909552938352600482529091208351815590830151909182019061049e9082610d2c565b5060408201516002820155606082015160038201556080820151600482015560a0820151600582015560c0820151600682015560e082015160078201556101008201516008820180546001600160a01b03199081166001600160a01b0393841617909155610120909301516009909201805490931691161790555050505050565b5f8181526003602052604090206008015481906001600160a01b031633146105985760405162461bcd60e51b815260206004820152602660248201527f4e6f2061677265656d656e7420666f756e64206265747765656e20746865207060448201526561727469657360d01b6064820152608401610220565b5f6105a2836108d6565b90507fbc951a1753a1f14874c460bd8c2d95ef6096ac9c4df4eaf22bfb7f23626e9743816040516105d591815260200190565b60405180910390a1803410156106445760405162461bcd60e51b815260206004820152602e60248201527f496e73756666696369656e742042616c616e636520506c6561736520746f707560448201526d1c081e5bdd5c881858d8dbdd5b9d60921b6064820152608401610220565b5f83815260046020526040808220600981015460089091015491516001600160a01b0391821693919092169183916108fc861502918691818181858888f19350505050158015610696573d5f803e3d5ffd5b506040516001600160a01b038216904780156108fc02915f818181858888f193505050501580156106c9573d5f803e3d5ffd5b5050505f9283525050600360205260409020600401805460ff19166001179055565b336106f4575f80fd5b5f8054908061070283610c8e565b909155505060408051610120810182525f805480835260208084018881528486018790526060850184905260016080860181905260a0860189905260c086018590523360e08701526101008601859052928452600390915293909120825181559251919291908201906107759082610d2c565b506040820151600282015560608201516003820155608082015160048201805460ff191691151591909117905560a0820151600582015560c0820151600682015560e08201516007820180546001600160a01b03199081166001600160a01b039384161790915561010090930151600890920180549093169116179055505050565b60036020525f90815260409020805460018201805491929161081890610ca6565b80601f016020809104026020016040519081016040528092919081815260200182805461084490610ca6565b801561088f5780601f106108665761010080835404028352916020019161088f565b820191905f5260205f20905b81548152906001019060200180831161087257829003601f168201915b505050506002830154600384015460048501546005860154600687015460078801546008909801549697949693955060ff9092169390926001600160a01b03918216911689565b5f81815260046020526040812060050154156109345760405162461bcd60e51b815260206004820152601760248201527f4e6f2041677265656d656e7420627920746869732069640000000000000000006044820152606401610220565b5f8281526004602081905260408220426005820181905591015461095791610de8565b5f8481526004602052604081206002015491925090610978610e1084610dfb565b6109829190610e1a565b6002819055949350505050565b60046020525f9081526040902080546001820180549192916109b090610ca6565b80601f01602080910402602001604051908101604052809291908181526020018280546109dc90610ca6565b8015610a275780601f106109fe57610100808354040283529160200191610a27565b820191905f5260205f20905b815481529060010190602001808311610a0a57829003601f168201915b50505060028401546003850154600486015460058701546006880154600789015460088a01546009909a015498999598949750929550909390926001600160a01b0391821691168a565b5f60208284031215610a81575f80fd5b5035919050565b634e487b7160e01b5f52604160045260245ffd5b5f805f60608486031215610aae575f80fd5b833567ffffffffffffffff80821115610ac5575f80fd5b818601915086601f830112610ad8575f80fd5b813581811115610aea57610aea610a88565b604051601f8201601f19908116603f01168101908382118183101715610b1257610b12610a88565b81604052828152896020848701011115610b2a575f80fd5b826020860160208301375f60208483010152809750505050505060208401359150604084013590509250925092565b5f81518084525f5b81811015610b7d57602081850181015186830182015201610b61565b505f602082860101526020601f19601f83011685010191505092915050565b5f6101208b8352806020840152610bb58184018c610b59565b604084019a909a5250506060810196909652931515608086015260a085019290925260c08401526001600160a01b0390811660e0840152166101009091015292915050565b5f6101408c8352806020840152610c138184018d610b59565b604084019b909b5250506060810197909752608087019590955260a086019390935260c085019190915260e08401526001600160a01b03908116610100840152166101209091015292915050565b634e487b7160e01b5f52601160045260245ffd5b80820180821115610c8857610c88610c61565b92915050565b5f60018201610c9f57610c9f610c61565b5060010190565b600181811c90821680610cba57607f821691505b602082108103610cd857634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610d27575f81815260208120601f850160051c81016020861015610d045750805b601f850160051c820191505b81811015610d2357828155600101610d10565b5050505b505050565b815167ffffffffffffffff811115610d4657610d46610a88565b610d5a81610d548454610ca6565b84610cde565b602080601f831160018114610d8d575f8415610d765750858301515b5f19600386901b1c1916600185901b178555610d23565b5f85815260208120601f198616915b82811015610dbb57888601518255948401946001909101908401610d9c565b5085821015610dd857878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b81810381811115610c8857610c88610c61565b5f82610e1557634e487b7160e01b5f52601260045260245ffd5b500490565b8082028115828204841417610c8857610c88610c6156fea26469706673582212206b4ea9b7b6955c230a66cd4d93701a475dd6c6ce04aa3b486e2064487f0d70dd64736f6c63430008140033";

    public static final String FUNC_AGREEMENTMAPPING = "AgreementMapping";

    public static final String FUNC_CALCULATERENT = "CalculateRent";

    public static final String FUNC_RETURNBIKE = "ReturnBike";

    public static final String FUNC_ADDBIKE = "addBike";

    public static final String FUNC_BIKE_MAPPING = "bike_mapping";

    public static final String FUNC_NO_OF_BIKE_AGREEMENTS = "no_of_bike_agreements";

    public static final String FUNC_NO_OF_BIKES = "no_of_bikes";

    public static final String FUNC_SIGNAGREEMENT = "signAgreement";

    public static final String FUNC_TOTAL = "total";

    public static final Event TOTALSRENT_EVENT = new Event("TotalsRent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

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

    public static List<TotalsRentEventResponse> getTotalsRentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TOTALSRENT_EVENT, transactionReceipt);
        ArrayList<TotalsRentEventResponse> responses = new ArrayList<TotalsRentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TotalsRentEventResponse typedResponse = new TotalsRentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rent = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TotalsRentEventResponse> totalsRentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TotalsRentEventResponse>() {
            @Override
            public TotalsRentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOTALSRENT_EVENT, log);
                TotalsRentEventResponse typedResponse = new TotalsRentEventResponse();
                typedResponse.log = log;
                typedResponse.rent = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TotalsRentEventResponse> totalsRentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOTALSRENT_EVENT));
        return totalsRentEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String>> AgreementMapping(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_AGREEMENTMAPPING, 
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
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CALCULATERENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> ReturnBike(BigInteger _id, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETURNBIKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> addBike(String _reg, BigInteger _rent, BigInteger _advance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDBIKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_reg), 
                new org.web3j.abi.datatypes.generated.Uint256(_rent), 
                new org.web3j.abi.datatypes.generated.Uint256(_advance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String>> bike_mapping(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BIKE_MAPPING, 
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
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NO_OF_BIKE_AGREEMENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> no_of_bikes() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NO_OF_BIKES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> signAgreement(BigInteger _index, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SIGNAGREEMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> total() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTAL, 
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

    public static class TotalsRentEventResponse extends BaseEventResponse {
        public BigInteger rent;
    }
}
