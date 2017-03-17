package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class TraitVTableWriter extends VTableWriter
{
	public abstract TraitVTable node();
	
	public StringBuilder generateHeader(StringBuilder builder, boolean full)
	{
		return builder;
	}
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return generateType(builder).append(" ").append(TraitVTable.IDENTIFIER);
	}
	
	@Override
	public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		return super.generateType(builder, checkArray, checkValueReference, checkAllocatedOnHeap).append("*");
	}
	
	@Override
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		return builder.append("nova_Interface_VTable");
	}
	
	public StringBuilder generateSource(StringBuilder builder, boolean full)
	{
		return builder;
	}
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, boolean full)
	{
		return super.generateSourceName(builder, full).append("_itable");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return generateSourceFragment(builder, false);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder, boolean full)
	{
		return full ? builder.append("&").append(generateSourceName(true)) : builder.append(0);
	}
	
	public StringBuilder generateDeclaration(StringBuilder builder)
	{
		NovaMethodDeclaration[] methods = node().getVirtualMethods();
		
		generateTypeName(builder).append(" ");
		generateSourceName(builder, true).append(" = ");
		
		builder.append("{\n");
		
		for (NovaMethodDeclaration method : methods)
		{
			if (method != null)
			{
				getWriter(method).generateInterfaceVTableSource(builder);
			}
			else
			{
				builder.append(0);
			}
			
			builder.append(",\n");
		}
		
		return builder.append("};\n");
	}
}