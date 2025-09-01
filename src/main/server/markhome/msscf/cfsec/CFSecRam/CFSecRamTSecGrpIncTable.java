
// Description: Java 11 in-memory RAM DbIO implementation for TSecGrpInc.

/*
 *	server.markhome.msscf.CFSec
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfsec.CFSecRam;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfsec.CFSecObj.*;
import server.markhome.msscf.cfsec.CFSecObj.*;

/*
 *	CFSecRamTSecGrpIncTable in-memory RAM DbIO implementation
 *	for TSecGrpInc.
 */
public class CFSecRamTSecGrpIncTable
	implements ICFSecTSecGrpIncTable
{
	private ICFSecSchema schema;
	private Map< CFSecTSecGrpIncPKey,
				CFSecTSecGrpIncBuff > dictByPKey
		= new HashMap< CFSecTSecGrpIncPKey,
				CFSecTSecGrpIncBuff >();
	private Map< CFSecTSecGrpIncByTenantIdxKey,
				Map< CFSecTSecGrpIncPKey,
					CFSecTSecGrpIncBuff >> dictByTenantIdx
		= new HashMap< CFSecTSecGrpIncByTenantIdxKey,
				Map< CFSecTSecGrpIncPKey,
					CFSecTSecGrpIncBuff >>();
	private Map< CFSecTSecGrpIncByGroupIdxKey,
				Map< CFSecTSecGrpIncPKey,
					CFSecTSecGrpIncBuff >> dictByGroupIdx
		= new HashMap< CFSecTSecGrpIncByGroupIdxKey,
				Map< CFSecTSecGrpIncPKey,
					CFSecTSecGrpIncBuff >>();
	private Map< CFSecTSecGrpIncByIncludeIdxKey,
				Map< CFSecTSecGrpIncPKey,
					CFSecTSecGrpIncBuff >> dictByIncludeIdx
		= new HashMap< CFSecTSecGrpIncByIncludeIdxKey,
				Map< CFSecTSecGrpIncPKey,
					CFSecTSecGrpIncBuff >>();
	private Map< CFSecTSecGrpIncByUIncludeIdxKey,
			CFSecTSecGrpIncBuff > dictByUIncludeIdx
		= new HashMap< CFSecTSecGrpIncByUIncludeIdxKey,
			CFSecTSecGrpIncBuff >();

	public CFSecRamTSecGrpIncTable( ICFSecSchema argSchema ) {
		schema = argSchema;
	}

	public void createTSecGrpInc( CFSecAuthorization Authorization,
		CFSecTSecGrpIncBuff Buff )
	{
		final String S_ProcName = "createTSecGrpInc";
		CFSecTSecGrpIncPKey pkey = schema.getFactoryTSecGrpInc().newPKey();
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredTSecGrpIncId( ((CFSecRamTenantTable)schema.getTableTenant()).nextTSecGrpIncIdGen( Authorization,
			Buff.getRequiredTenantId() ) );
		Buff.setRequiredTenantId( pkey.getRequiredTenantId() );
		Buff.setRequiredTSecGrpIncId( pkey.getRequiredTSecGrpIncId() );
		CFSecTSecGrpIncByTenantIdxKey keyTenantIdx = schema.getFactoryTSecGrpInc().newTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( Buff.getRequiredTenantId() );

		CFSecTSecGrpIncByGroupIdxKey keyGroupIdx = schema.getFactoryTSecGrpInc().newGroupIdxKey();
		keyGroupIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		keyGroupIdx.setRequiredTSecGroupId( Buff.getRequiredTSecGroupId() );

		CFSecTSecGrpIncByIncludeIdxKey keyIncludeIdx = schema.getFactoryTSecGrpInc().newIncludeIdxKey();
		keyIncludeIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		keyIncludeIdx.setRequiredIncludeGroupId( Buff.getRequiredIncludeGroupId() );

		CFSecTSecGrpIncByUIncludeIdxKey keyUIncludeIdx = schema.getFactoryTSecGrpInc().newUIncludeIdxKey();
		keyUIncludeIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		keyUIncludeIdx.setRequiredTSecGroupId( Buff.getRequiredTSecGroupId() );
		keyUIncludeIdx.setRequiredIncludeGroupId( Buff.getRequiredIncludeGroupId() );

		// Validate unique indexes

		if( dictByPKey.containsKey( pkey ) ) {
			throw new CFLibPrimaryKeyNotNewException( getClass(), S_ProcName, pkey );
		}

		if( dictByUIncludeIdx.containsKey( keyUIncludeIdx ) ) {
			throw new CFLibUniqueIndexViolationException( getClass(),
				S_ProcName,
				"TSecGrpIncUIncludeIdx",
				keyUIncludeIdx );
		}

		// Validate foreign keys

		{
			boolean allNull = true;
			allNull = false;
			if( ! allNull ) {
				if( null == schema.getTableTenant().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						S_ProcName,
						"Owner",
						"TSecGrpIncTenant",
						"Tenant",
						null );
				}
			}
		}

		{
			boolean allNull = true;
			allNull = false;
			allNull = false;
			if( ! allNull ) {
				if( null == schema.getTableTSecGroup().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId(),
						Buff.getRequiredTSecGroupId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						S_ProcName,
						"Container",
						"TSecGrpIncGroup",
						"TSecGroup",
						null );
				}
			}
		}

		// Proceed with adding the new record

		dictByPKey.put( pkey, Buff );

		Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictTenantIdx;
		if( dictByTenantIdx.containsKey( keyTenantIdx ) ) {
			subdictTenantIdx = dictByTenantIdx.get( keyTenantIdx );
		}
		else {
			subdictTenantIdx = new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByTenantIdx.put( keyTenantIdx, subdictTenantIdx );
		}
		subdictTenantIdx.put( pkey, Buff );

		Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictGroupIdx;
		if( dictByGroupIdx.containsKey( keyGroupIdx ) ) {
			subdictGroupIdx = dictByGroupIdx.get( keyGroupIdx );
		}
		else {
			subdictGroupIdx = new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByGroupIdx.put( keyGroupIdx, subdictGroupIdx );
		}
		subdictGroupIdx.put( pkey, Buff );

		Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictIncludeIdx;
		if( dictByIncludeIdx.containsKey( keyIncludeIdx ) ) {
			subdictIncludeIdx = dictByIncludeIdx.get( keyIncludeIdx );
		}
		else {
			subdictIncludeIdx = new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByIncludeIdx.put( keyIncludeIdx, subdictIncludeIdx );
		}
		subdictIncludeIdx.put( pkey, Buff );

		dictByUIncludeIdx.put( keyUIncludeIdx, Buff );

	}

	public CFSecTSecGrpIncBuff readDerived( CFSecAuthorization Authorization,
		CFSecTSecGrpIncPKey PKey )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerived";
		CFSecTSecGrpIncPKey key = schema.getFactoryTSecGrpInc().newPKey();
		key.setRequiredTenantId( PKey.getRequiredTenantId() );
		key.setRequiredTSecGrpIncId( PKey.getRequiredTSecGrpIncId() );
		CFSecTSecGrpIncBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGrpIncBuff lockDerived( CFSecAuthorization Authorization,
		CFSecTSecGrpIncPKey PKey )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerived";
		CFSecTSecGrpIncPKey key = schema.getFactoryTSecGrpInc().newPKey();
		key.setRequiredTenantId( PKey.getRequiredTenantId() );
		key.setRequiredTSecGrpIncId( PKey.getRequiredTSecGrpIncId() );
		CFSecTSecGrpIncBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGrpIncBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "CFSecRamTSecGrpInc.readAllDerived";
		CFSecTSecGrpIncBuff[] retList = new CFSecTSecGrpIncBuff[ dictByPKey.values().size() ];
		Iterator< CFSecTSecGrpIncBuff > iter = dictByPKey.values().iterator();
		int idx = 0;
		while( iter.hasNext() ) {
			retList[ idx++ ] = iter.next();
		}
		return( retList );
	}

	public CFSecTSecGrpIncBuff[] readDerivedByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerivedByTenantIdx";
		CFSecTSecGrpIncByTenantIdxKey key = schema.getFactoryTSecGrpInc().newTenantIdxKey();
		key.setRequiredTenantId( TenantId );

		CFSecTSecGrpIncBuff[] recArray;
		if( dictByTenantIdx.containsKey( key ) ) {
			Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictTenantIdx
				= dictByTenantIdx.get( key );
			recArray = new CFSecTSecGrpIncBuff[ subdictTenantIdx.size() ];
			Iterator< CFSecTSecGrpIncBuff > iter = subdictTenantIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictTenantIdx
				= new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByTenantIdx.put( key, subdictTenantIdx );
			recArray = new CFSecTSecGrpIncBuff[0];
		}
		return( recArray );
	}

	public CFSecTSecGrpIncBuff[] readDerivedByGroupIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerivedByGroupIdx";
		CFSecTSecGrpIncByGroupIdxKey key = schema.getFactoryTSecGrpInc().newGroupIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );

		CFSecTSecGrpIncBuff[] recArray;
		if( dictByGroupIdx.containsKey( key ) ) {
			Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictGroupIdx
				= dictByGroupIdx.get( key );
			recArray = new CFSecTSecGrpIncBuff[ subdictGroupIdx.size() ];
			Iterator< CFSecTSecGrpIncBuff > iter = subdictGroupIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictGroupIdx
				= new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByGroupIdx.put( key, subdictGroupIdx );
			recArray = new CFSecTSecGrpIncBuff[0];
		}
		return( recArray );
	}

	public CFSecTSecGrpIncBuff[] readDerivedByIncludeIdx( CFSecAuthorization Authorization,
		long TenantId,
		int IncludeGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerivedByIncludeIdx";
		CFSecTSecGrpIncByIncludeIdxKey key = schema.getFactoryTSecGrpInc().newIncludeIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredIncludeGroupId( IncludeGroupId );

		CFSecTSecGrpIncBuff[] recArray;
		if( dictByIncludeIdx.containsKey( key ) ) {
			Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictIncludeIdx
				= dictByIncludeIdx.get( key );
			recArray = new CFSecTSecGrpIncBuff[ subdictIncludeIdx.size() ];
			Iterator< CFSecTSecGrpIncBuff > iter = subdictIncludeIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdictIncludeIdx
				= new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByIncludeIdx.put( key, subdictIncludeIdx );
			recArray = new CFSecTSecGrpIncBuff[0];
		}
		return( recArray );
	}

	public CFSecTSecGrpIncBuff readDerivedByUIncludeIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId,
		int IncludeGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerivedByUIncludeIdx";
		CFSecTSecGrpIncByUIncludeIdxKey key = schema.getFactoryTSecGrpInc().newUIncludeIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );
		key.setRequiredIncludeGroupId( IncludeGroupId );

		CFSecTSecGrpIncBuff buff;
		if( dictByUIncludeIdx.containsKey( key ) ) {
			buff = dictByUIncludeIdx.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGrpIncBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		long TSecGrpIncId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readDerivedByIdIdx() ";
		CFSecTSecGrpIncPKey key = schema.getFactoryTSecGrpInc().newPKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGrpIncId( TSecGrpIncId );

		CFSecTSecGrpIncBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGrpIncBuff readBuff( CFSecAuthorization Authorization,
		CFSecTSecGrpIncPKey PKey )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readBuff";
		CFSecTSecGrpIncBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a017" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGrpIncBuff lockBuff( CFSecAuthorization Authorization,
		CFSecTSecGrpIncPKey PKey )
	{
		final String S_ProcName = "lockBuff";
		CFSecTSecGrpIncBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a017" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGrpIncBuff[] readAllBuff( CFSecAuthorization Authorization )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readAllBuff";
		CFSecTSecGrpIncBuff buff;
		ArrayList<CFSecTSecGrpIncBuff> filteredList = new ArrayList<CFSecTSecGrpIncBuff>();
		CFSecTSecGrpIncBuff[] buffList = readAllDerived( Authorization );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a017" ) ) {
				filteredList.add( buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGrpIncBuff[0] ) );
	}

	/**
	 *	Read a page of all the specific TSecGrpInc buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TSecGrpInc instances in the database accessible for the Authorization.
	 */
	public CFSecTSecGrpIncBuff[] pageAllBuff( CFSecAuthorization Authorization,
		Long priorTenantId,
		Long priorTSecGrpIncId )
	{
		final String S_ProcName = "pageAllBuff";
		throw new CFLibNotImplementedYetException( getClass(), S_ProcName );
	}

	public CFSecTSecGrpIncBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		long TSecGrpIncId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readBuffByIdIdx() ";
		CFSecTSecGrpIncBuff buff = readDerivedByIdIdx( Authorization,
			TenantId,
			TSecGrpIncId );
		if( ( buff != null ) && buff.getClassCode().equals( "a017" ) ) {
			return( (CFSecTSecGrpIncBuff)buff );
		}
		else {
			return( null );
		}
	}

	public CFSecTSecGrpIncBuff[] readBuffByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readBuffByTenantIdx() ";
		CFSecTSecGrpIncBuff buff;
		ArrayList<CFSecTSecGrpIncBuff> filteredList = new ArrayList<CFSecTSecGrpIncBuff>();
		CFSecTSecGrpIncBuff[] buffList = readDerivedByTenantIdx( Authorization,
			TenantId );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a017" ) ) {
				filteredList.add( (CFSecTSecGrpIncBuff)buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGrpIncBuff[0] ) );
	}

	public CFSecTSecGrpIncBuff[] readBuffByGroupIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readBuffByGroupIdx() ";
		CFSecTSecGrpIncBuff buff;
		ArrayList<CFSecTSecGrpIncBuff> filteredList = new ArrayList<CFSecTSecGrpIncBuff>();
		CFSecTSecGrpIncBuff[] buffList = readDerivedByGroupIdx( Authorization,
			TenantId,
			TSecGroupId );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a017" ) ) {
				filteredList.add( (CFSecTSecGrpIncBuff)buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGrpIncBuff[0] ) );
	}

	public CFSecTSecGrpIncBuff[] readBuffByIncludeIdx( CFSecAuthorization Authorization,
		long TenantId,
		int IncludeGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readBuffByIncludeIdx() ";
		CFSecTSecGrpIncBuff buff;
		ArrayList<CFSecTSecGrpIncBuff> filteredList = new ArrayList<CFSecTSecGrpIncBuff>();
		CFSecTSecGrpIncBuff[] buffList = readDerivedByIncludeIdx( Authorization,
			TenantId,
			IncludeGroupId );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a017" ) ) {
				filteredList.add( (CFSecTSecGrpIncBuff)buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGrpIncBuff[0] ) );
	}

	public CFSecTSecGrpIncBuff readBuffByUIncludeIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId,
		int IncludeGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGrpInc.readBuffByUIncludeIdx() ";
		CFSecTSecGrpIncBuff buff = readDerivedByUIncludeIdx( Authorization,
			TenantId,
			TSecGroupId,
			IncludeGroupId );
		if( ( buff != null ) && buff.getClassCode().equals( "a017" ) ) {
			return( (CFSecTSecGrpIncBuff)buff );
		}
		else {
			return( null );
		}
	}

	/**
	 *	Read a page array of the specific TSecGrpInc buffer instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argTenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTSecGrpIncBuff[] pageBuffByTenantIdx( CFSecAuthorization Authorization,
		long TenantId,
		Long priorTenantId,
		Long priorTSecGrpIncId )
	{
		final String S_ProcName = "pageBuffByTenantIdx";
		throw new CFLibNotImplementedYetException( getClass(), S_ProcName );
	}

	/**
	 *	Read a page array of the specific TSecGrpInc buffer instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argTenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	argTSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTSecGrpIncBuff[] pageBuffByGroupIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId,
		Long priorTenantId,
		Long priorTSecGrpIncId )
	{
		final String S_ProcName = "pageBuffByGroupIdx";
		throw new CFLibNotImplementedYetException( getClass(), S_ProcName );
	}

	/**
	 *	Read a page array of the specific TSecGrpInc buffer instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argTenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	argIncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTSecGrpIncBuff[] pageBuffByIncludeIdx( CFSecAuthorization Authorization,
		long TenantId,
		int IncludeGroupId,
		Long priorTenantId,
		Long priorTSecGrpIncId )
	{
		final String S_ProcName = "pageBuffByIncludeIdx";
		throw new CFLibNotImplementedYetException( getClass(), S_ProcName );
	}

	public void updateTSecGrpInc( CFSecAuthorization Authorization,
		CFSecTSecGrpIncBuff Buff )
	{
		CFSecTSecGrpIncPKey pkey = schema.getFactoryTSecGrpInc().newPKey();
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredTSecGrpIncId( Buff.getRequiredTSecGrpIncId() );
		CFSecTSecGrpIncBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			throw new CFLibStaleCacheDetectedException( getClass(),
				"updateTSecGrpInc",
				"Existing record not found",
				"TSecGrpInc",
				pkey );
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() ) {
			throw new CFLibCollisionDetectedException( getClass(),
				"updateTSecGrpInc",
				pkey );
		}
		Buff.setRequiredRevision( Buff.getRequiredRevision() + 1 );
		CFSecTSecGrpIncByTenantIdxKey existingKeyTenantIdx = schema.getFactoryTSecGrpInc().newTenantIdxKey();
		existingKeyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		CFSecTSecGrpIncByTenantIdxKey newKeyTenantIdx = schema.getFactoryTSecGrpInc().newTenantIdxKey();
		newKeyTenantIdx.setRequiredTenantId( Buff.getRequiredTenantId() );

		CFSecTSecGrpIncByGroupIdxKey existingKeyGroupIdx = schema.getFactoryTSecGrpInc().newGroupIdxKey();
		existingKeyGroupIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		existingKeyGroupIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );

		CFSecTSecGrpIncByGroupIdxKey newKeyGroupIdx = schema.getFactoryTSecGrpInc().newGroupIdxKey();
		newKeyGroupIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		newKeyGroupIdx.setRequiredTSecGroupId( Buff.getRequiredTSecGroupId() );

		CFSecTSecGrpIncByIncludeIdxKey existingKeyIncludeIdx = schema.getFactoryTSecGrpInc().newIncludeIdxKey();
		existingKeyIncludeIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		existingKeyIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );

		CFSecTSecGrpIncByIncludeIdxKey newKeyIncludeIdx = schema.getFactoryTSecGrpInc().newIncludeIdxKey();
		newKeyIncludeIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		newKeyIncludeIdx.setRequiredIncludeGroupId( Buff.getRequiredIncludeGroupId() );

		CFSecTSecGrpIncByUIncludeIdxKey existingKeyUIncludeIdx = schema.getFactoryTSecGrpInc().newUIncludeIdxKey();
		existingKeyUIncludeIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		existingKeyUIncludeIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );
		existingKeyUIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );

		CFSecTSecGrpIncByUIncludeIdxKey newKeyUIncludeIdx = schema.getFactoryTSecGrpInc().newUIncludeIdxKey();
		newKeyUIncludeIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		newKeyUIncludeIdx.setRequiredTSecGroupId( Buff.getRequiredTSecGroupId() );
		newKeyUIncludeIdx.setRequiredIncludeGroupId( Buff.getRequiredIncludeGroupId() );

		// Check unique indexes

		if( ! existingKeyUIncludeIdx.equals( newKeyUIncludeIdx ) ) {
			if( dictByUIncludeIdx.containsKey( newKeyUIncludeIdx ) ) {
				throw new CFLibUniqueIndexViolationException( getClass(),
					"updateTSecGrpInc",
					"TSecGrpIncUIncludeIdx",
					newKeyUIncludeIdx );
			}
		}

		// Validate foreign keys

		{
			boolean allNull = true;

			if( allNull ) {
				if( null == schema.getTableTenant().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						"updateTSecGrpInc",
						"Owner",
						"TSecGrpIncTenant",
						"Tenant",
						null );
				}
			}
		}

		{
			boolean allNull = true;

			if( allNull ) {
				if( null == schema.getTableTSecGroup().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId(),
						Buff.getRequiredTSecGroupId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						"updateTSecGrpInc",
						"Container",
						"TSecGrpIncGroup",
						"TSecGroup",
						null );
				}
			}
		}

		// Update is valid

		Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdict;

		dictByPKey.remove( pkey );
		dictByPKey.put( pkey, Buff );

		subdict = dictByTenantIdx.get( existingKeyTenantIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByTenantIdx.containsKey( newKeyTenantIdx ) ) {
			subdict = dictByTenantIdx.get( newKeyTenantIdx );
		}
		else {
			subdict = new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByTenantIdx.put( newKeyTenantIdx, subdict );
		}
		subdict.put( pkey, Buff );

		subdict = dictByGroupIdx.get( existingKeyGroupIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByGroupIdx.containsKey( newKeyGroupIdx ) ) {
			subdict = dictByGroupIdx.get( newKeyGroupIdx );
		}
		else {
			subdict = new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByGroupIdx.put( newKeyGroupIdx, subdict );
		}
		subdict.put( pkey, Buff );

		subdict = dictByIncludeIdx.get( existingKeyIncludeIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByIncludeIdx.containsKey( newKeyIncludeIdx ) ) {
			subdict = dictByIncludeIdx.get( newKeyIncludeIdx );
		}
		else {
			subdict = new HashMap< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff >();
			dictByIncludeIdx.put( newKeyIncludeIdx, subdict );
		}
		subdict.put( pkey, Buff );

		dictByUIncludeIdx.remove( existingKeyUIncludeIdx );
		dictByUIncludeIdx.put( newKeyUIncludeIdx, Buff );

	}

	public void deleteTSecGrpInc( CFSecAuthorization Authorization,
		CFSecTSecGrpIncBuff Buff )
	{
		final String S_ProcName = "CFSecRamTSecGrpIncTable.deleteTSecGrpInc() ";
		String classCode;
		CFSecTSecGrpIncPKey pkey = schema.getFactoryTSecGrpInc().newPKey();
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredTSecGrpIncId( Buff.getRequiredTSecGrpIncId() );
		CFSecTSecGrpIncBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			return;
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() )
		{
			throw new CFLibCollisionDetectedException( getClass(),
				"deleteTSecGrpInc",
				pkey );
		}
		CFSecTSecGrpIncByTenantIdxKey keyTenantIdx = schema.getFactoryTSecGrpInc().newTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		CFSecTSecGrpIncByGroupIdxKey keyGroupIdx = schema.getFactoryTSecGrpInc().newGroupIdxKey();
		keyGroupIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyGroupIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );

		CFSecTSecGrpIncByIncludeIdxKey keyIncludeIdx = schema.getFactoryTSecGrpInc().newIncludeIdxKey();
		keyIncludeIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );

		CFSecTSecGrpIncByUIncludeIdxKey keyUIncludeIdx = schema.getFactoryTSecGrpInc().newUIncludeIdxKey();
		keyUIncludeIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyUIncludeIdx.setRequiredTSecGroupId( existing.getRequiredTSecGroupId() );
		keyUIncludeIdx.setRequiredIncludeGroupId( existing.getRequiredIncludeGroupId() );

		// Validate reverse foreign keys

		// Delete is valid
		Map< CFSecTSecGrpIncPKey, CFSecTSecGrpIncBuff > subdict;

		dictByPKey.remove( pkey );

		subdict = dictByTenantIdx.get( keyTenantIdx );
		subdict.remove( pkey );

		subdict = dictByGroupIdx.get( keyGroupIdx );
		subdict.remove( pkey );

		subdict = dictByIncludeIdx.get( keyIncludeIdx );
		subdict.remove( pkey );

		dictByUIncludeIdx.remove( keyUIncludeIdx );

	}
	public void deleteTSecGrpIncByIdIdx( CFSecAuthorization Authorization,
		long argTenantId,
		long argTSecGrpIncId )
	{
		CFSecTSecGrpIncPKey key = schema.getFactoryTSecGrpInc().newPKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredTSecGrpIncId( argTSecGrpIncId );
		deleteTSecGrpIncByIdIdx( Authorization, key );
	}

	public void deleteTSecGrpIncByIdIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpIncPKey argKey )
	{
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		CFSecTSecGrpIncBuff cur;
		LinkedList<CFSecTSecGrpIncBuff> matchSet = new LinkedList<CFSecTSecGrpIncBuff>();
		Iterator<CFSecTSecGrpIncBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGrpIncBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGrpInc().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGrpIncId() );
			deleteTSecGrpInc( Authorization, cur );
		}
	}

	public void deleteTSecGrpIncByTenantIdx( CFSecAuthorization Authorization,
		long argTenantId )
	{
		CFSecTSecGrpIncByTenantIdxKey key = schema.getFactoryTSecGrpInc().newTenantIdxKey();
		key.setRequiredTenantId( argTenantId );
		deleteTSecGrpIncByTenantIdx( Authorization, key );
	}

	public void deleteTSecGrpIncByTenantIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpIncByTenantIdxKey argKey )
	{
		CFSecTSecGrpIncBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGrpIncBuff> matchSet = new LinkedList<CFSecTSecGrpIncBuff>();
		Iterator<CFSecTSecGrpIncBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGrpIncBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGrpInc().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGrpIncId() );
			deleteTSecGrpInc( Authorization, cur );
		}
	}

	public void deleteTSecGrpIncByGroupIdx( CFSecAuthorization Authorization,
		long argTenantId,
		int argTSecGroupId )
	{
		CFSecTSecGrpIncByGroupIdxKey key = schema.getFactoryTSecGrpInc().newGroupIdxKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredTSecGroupId( argTSecGroupId );
		deleteTSecGrpIncByGroupIdx( Authorization, key );
	}

	public void deleteTSecGrpIncByGroupIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpIncByGroupIdxKey argKey )
	{
		CFSecTSecGrpIncBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGrpIncBuff> matchSet = new LinkedList<CFSecTSecGrpIncBuff>();
		Iterator<CFSecTSecGrpIncBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGrpIncBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGrpInc().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGrpIncId() );
			deleteTSecGrpInc( Authorization, cur );
		}
	}

	public void deleteTSecGrpIncByIncludeIdx( CFSecAuthorization Authorization,
		long argTenantId,
		int argIncludeGroupId )
	{
		CFSecTSecGrpIncByIncludeIdxKey key = schema.getFactoryTSecGrpInc().newIncludeIdxKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredIncludeGroupId( argIncludeGroupId );
		deleteTSecGrpIncByIncludeIdx( Authorization, key );
	}

	public void deleteTSecGrpIncByIncludeIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpIncByIncludeIdxKey argKey )
	{
		CFSecTSecGrpIncBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGrpIncBuff> matchSet = new LinkedList<CFSecTSecGrpIncBuff>();
		Iterator<CFSecTSecGrpIncBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGrpIncBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGrpInc().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGrpIncId() );
			deleteTSecGrpInc( Authorization, cur );
		}
	}

	public void deleteTSecGrpIncByUIncludeIdx( CFSecAuthorization Authorization,
		long argTenantId,
		int argTSecGroupId,
		int argIncludeGroupId )
	{
		CFSecTSecGrpIncByUIncludeIdxKey key = schema.getFactoryTSecGrpInc().newUIncludeIdxKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredTSecGroupId( argTSecGroupId );
		key.setRequiredIncludeGroupId( argIncludeGroupId );
		deleteTSecGrpIncByUIncludeIdx( Authorization, key );
	}

	public void deleteTSecGrpIncByUIncludeIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpIncByUIncludeIdxKey argKey )
	{
		CFSecTSecGrpIncBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGrpIncBuff> matchSet = new LinkedList<CFSecTSecGrpIncBuff>();
		Iterator<CFSecTSecGrpIncBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGrpIncBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGrpInc().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGrpIncId() );
			deleteTSecGrpInc( Authorization, cur );
		}
	}

	public void releasePreparedStatements() {
	}
}
